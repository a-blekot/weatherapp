package com.anadi.weatherinfo.view.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.databinding.DetailsFragmentBinding
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.Status
import com.anadi.weatherinfo.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : BaseFragment(R.layout.details_fragment) {

    private val binding: DetailsFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.locationId = DetailsFragmentArgs.fromBundle(requireArguments()).locationId
        viewModel.providerId = DetailsFragmentArgs.fromBundle(requireArguments()).providerId

        viewModel.detailsNotifier.observe(viewLifecycleOwner, Observer { update(it) })
        binding.updateButton.setOnClickListener { viewModel.update() }
    }

    override fun onResume() {
        super.onResume()
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

        if (data == null) {
            Timber.d("LocationWithWeathers is null!")
            return
        }

        val location = data.location
        val weather = data.weathers
                .filter { it.providerId == viewModel.providerId }
                .getOrNull(0)

        binding.weatherIcon.setImageResource(IconMap.getIconId("01d"))
        binding.windIcon.rotation = weather?.windDegree?.toFloat() ?: 0F
        binding.locationName.text = getString(R.string.location_name, location.city, location.country.name)
        binding.temp.text = getString(R.string.temp_celsium, weather?.temp ?: 0)
        binding.wind.text = getString(R.string.wind_speed_ms, weather?.windSpeed ?: 0)
        binding.pressure.text = getString(R.string.pressure, weather?.pressure ?: 0)
        binding.humidity.text = getString(R.string.humidity, weather?.humidity ?: 0)
    }

}