package com.anadi.weatherinfo.view.ui.providers

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.weather.WeatherCodes
import com.anadi.weatherinfo.databinding.ProvidersFragmentBinding
import com.anadi.weatherinfo.utils.DateFormats
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.Status
import com.anadi.weatherinfo.view.ui.BaseFragment
import com.anadi.weatherinfo.view.ui.details.DetailsFragmentArgs
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class ProvidersFragment : BaseFragment(R.layout.providers_fragment), ProvidersAdapter.Listener {

    private val binding: ProvidersFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var weatherCodes: WeatherCodes

    private lateinit var viewModel: ProvidersViewModel

    private lateinit var adapter: ProvidersAdapter

    private lateinit var location: Location

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProvidersViewModel::class.java)
        viewModel.id = DetailsFragmentArgs.fromBundle(requireArguments()).locationId

        adapter = ProvidersAdapter(this, weatherCodes)
        binding.recyclerView.apply {
            adapter = this@ProvidersFragment.adapter
            addItemDecoration(DividerItemDecoration(binding.recyclerView.context, DividerItemDecoration.VERTICAL))
        }

        viewModel.details.observe(viewLifecycleOwner, Observer { update(it) })
        viewModel.mergedWeather.observe(viewLifecycleOwner, Observer { updateMerged(it) })

        viewModel.fetch()
    }

    private fun loading() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    private fun error(message: String?) {
        binding.progressBar.root.visibility = View.GONE
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
        binding.progressBar.root.visibility = View.GONE
        binding.noData.visibility = View.GONE

        if (data == null || data.weathers.isEmpty()) {
            if (data == null) {
                Timber.d("LocationWithWeathers is null!")
            }

            binding.noData.visibility = View.VISIBLE
            return
        }

        location = data.location
        adapter.location = data.location
        adapter.dataset = data.weathers
    }

    private fun updateMerged(weather: Weather) {

        val weatherCode = weatherCodes.from(weather.code)

        with(binding.merged) {
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
