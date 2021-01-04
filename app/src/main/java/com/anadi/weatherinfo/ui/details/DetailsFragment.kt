package com.anadi.weatherinfo.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.databinding.DetailsFragmentBinding
import com.anadi.weatherinfo.repository.IconMap
import com.anadi.weatherinfo.repository.data.WeatherInfo
import com.anadi.weatherinfo.ui.BaseFragment
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.Status
import es.dmoral.toasty.Toasty
import timber.log.Timber
import java.text.DateFormat
import java.util.*
import javax.inject.Inject

class DetailsFragment : BaseFragment(R.layout.details_fragment) {

    private val binding: DetailsFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.id = DetailsFragmentArgs.fromBundle(requireArguments()).locationId
        viewModel.subscribe()

        viewModel.detailsNotifier.observe(viewLifecycleOwner, Observer { update(it) })

        binding.updateButton.setOnClickListener { viewModel.update() }
    }

    fun loading() {
        binding.progressBar.root.visibility = View.VISIBLE
    }

    fun error(message: String?) {
        binding.progressBar.root.visibility = View.GONE
        message?.let { Toasty.error(requireContext(), it, Toast.LENGTH_LONG).show() }
    }

    fun update(resource: Resource<WeatherInfo>) {
        when (resource.status) {
            Status.SUCCESS -> update(resource.data)
            Status.LOADING -> loading()
            Status.ERROR -> error(resource.message(requireContext()))
        }
    }

    fun update(info: WeatherInfo?) {
        binding.progressBar.root.visibility = View.GONE

        if (info == null) {
            Timber.d("WeatherInfo is null!")
            return
        }

        binding.weatherIcon.setImageResource(IconMap.getIconId(info.weather[0].icon))
        binding.windDegreeIcon.rotation = info.wind.deg.toFloat()
        binding.locationName.text = getString(R.string.location_name, info.name, info.sys.country)
        var minutes = info.timezone / 60
        var hours = minutes / 60
        minutes %= 60 // to get right time in format hh:mm.
        val sign = if (hours > 0) "+" else if (hours < 0) "-" else ""
        hours = Math.abs(hours)
        binding.timezone.text = getString(R.string.timezone, sign, hours, minutes)
        binding.temp.text = getString(R.string.temp_celsium, info.main.temp)
        binding.windSpeed.text = getString(R.string.wind_speed_ms, info.wind.speed)
        binding.tempFeelsLike.text = getString(R.string.feels_like_celsium, info.main.feelsLike)
        binding.pressure.text = getString(R.string.pressure, info.main.pressure)
        binding.humidity.text = getString(R.string.humidity, info.main.humidity)

        //        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        val format = DateFormat.getTimeInstance()
        val sunrise = Date(info.sys.sunrise * 1000L)
        val sunset = Date(info.sys.sunset * 1000L)
        binding.sysSunrise.text = getString(R.string.sunrise, format.format(sunrise))
        binding.sysSunset.text = getString(R.string.sunset, format.format(sunset))
        binding.coordinates.text = getString(R.string.coordinates, info.coord.lat, info.coord.lon)
    }

}