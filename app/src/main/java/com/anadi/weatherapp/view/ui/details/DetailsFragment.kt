package com.anadi.weatherapp.view.ui.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherapp.R
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.databinding.DetailsFragmentStartBinding
import com.anadi.weatherapp.utils.DateFormats
import com.anadi.weatherapp.utils.Resource
import com.anadi.weatherapp.utils.Status
import com.anadi.weatherapp.view.ui.BaseFragment
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : BaseFragment(R.layout.details_fragment_start) {

    private val binding: DetailsFragmentStartBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var weatherCodes: WeatherCodes

    private lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.locationId = DetailsFragmentArgs.fromBundle(requireArguments()).locationId
        viewModel.providerId = DetailsFragmentArgs.fromBundle(requireArguments()).providerId

        viewModel.detailsNotifier.observe(viewLifecycleOwner, { update(it) })
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
        val weather = data.weathers.firstOrNull { it.providerId == viewModel.providerId }

        binding.weatherIcon.setImageResource(weatherCodes.from(weather?.code ?: 0).getIcon(location))
        binding.windIcon.rotation = weather?.windDegree?.toFloat() ?: 0F
        binding.description.text = getString(weatherCodes.from(weather?.code ?: 0).description)
        binding.locationName.text = location.name
        binding.locationAddress.text = location.address
        binding.coordinates.text = location.coord.toString()
        binding.timezone.text = getString(R.string.timezone, location.timeZone.toString())
        binding.temp.text = getString(R.string.temp_celsium, weather?.temp ?: 0)
        binding.tempFeelsLike.text = getString(R.string.temp_feels_like_celsium, weather?.tempFeelsLike ?: 0)
        binding.wind.text = getString(R.string.wind_speed_ms, weather?.windSpeed ?: 0)
        binding.pressure.text = getString(R.string.pressure, weather?.pressure ?: 0)
        binding.humidity.text = getString(R.string.humidity, weather?.humidity ?: 0)
        binding.clouds.text = getString(R.string.clouds, weather?.clouds ?: 0)
        binding.sunrise.text = getString(R.string.sunrise, location.sunrise.toString(DateFormats.sunTime))
        binding.sunset.text = getString(R.string.sunset, location.sunset.toString(DateFormats.sunTime))
    }
}
