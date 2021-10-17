package com.anadi.weatherapp.view.ui.providers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.databinding.ProvidersFragmentBinding
import com.anadi.weatherapp.utils.DateFormats
import com.anadi.weatherapp.utils.Resource
import com.anadi.weatherapp.utils.Status
import com.anadi.weatherapp.view.ui.BaseFragment
import com.anadi.weatherapp.view.ui.details.DetailsFragmentArgs
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class ProvidersFragment : BaseFragment(R.layout.providers_fragment), ProvidersAdapter.Listener {

    private var binding: ProvidersFragmentBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var weatherCodes: WeatherCodes

    private lateinit var viewModel: ProvidersViewModel

    private lateinit var adapter: ProvidersAdapter

    private lateinit var location: Location

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            ProvidersFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProvidersViewModel::class.java)
        viewModel.id = DetailsFragmentArgs.fromBundle(requireArguments()).locationId

        adapter = ProvidersAdapter(this, weatherCodes)
        binding?.recyclerView?.apply {
            adapter = this@ProvidersFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.details.observe(viewLifecycleOwner) { update(it) }
        viewModel.mergedWeather.observe(viewLifecycleOwner) { updateMerged(it) }

        viewModel.fetch()
    }

    private fun loading() =
            setProgressVisibility(true)

    private fun setProgressVisibility(isVisible: Boolean) {
        binding?.progressBar?.root?.isVisible = isVisible
    }

    private fun error(message: String?) {
        setProgressVisibility(false)
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    private fun update(resource: Resource<LocationWithWeathers>) {
        when (resource.status) {
            Status.SUCCESS -> update(resource.data)
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
    }

    private fun update(data: LocationWithWeathers?) {
        setProgressVisibility(false)
        binding?.noData?.isVisible = false

        if (data == null || data.weathers.isEmpty()) {
            if (data == null) {
                Timber.d("LocationWithWeathers is null!")
            }

            binding?.noData?.isVisible = true
            return
        }

        location = data.location
        adapter.location = data.location
        adapter.dataset = data.weathers
    }

    private fun updateMerged(weather: Weather) {

        val weatherCode = weatherCodes.from(weather.code)

        binding?.merged?.run {
            providerName.text = location.name
            lastUpdateTime.text = DateFormats.defaultTime.print(weather.dataCalcTimestamp)

            icon.setImageResource(weatherCode.getIcon(location))
            description.text = getString(weatherCode.description)

            temp.text = getString(R.string.temp_short_celsium, weather.temp)
            windSpeed.text = getString(R.string.wind_speed_short_ms, weather.windSpeed)

            pressure.text = getString(R.string.pressure_short, weather.pressure)
            humidity.text = getString(R.string.humidity_short, weather.humidity)
        }
    }

    override fun onSelected(weather: Weather) {
        val action = ProvidersFragmentDirections.actionProvidersToDetails()
        action.locationId = weather.locationId
        action.providerId = weather.providerId
        findNavController().navigate(action)
    }
}
