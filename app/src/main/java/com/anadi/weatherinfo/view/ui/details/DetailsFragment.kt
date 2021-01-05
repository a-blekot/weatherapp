package com.anadi.weatherinfo.view.ui.details

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
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.data.WeatherInfo
import com.anadi.weatherinfo.view.ui.BaseFragment
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
        binding.windIcon.rotation = info.wind.deg.toFloat()
        binding.locationName.text = getString(R.string.location_name, info.name, info.sys.country)
        binding.temp.text = getString(R.string.temp_celsium, info.main.temp)
        binding.wind.text = getString(R.string.wind_speed_ms, info.wind.speed)
        binding.pressure.text = getString(R.string.pressure, info.main.pressure)
        binding.humidity.text = getString(R.string.humidity, info.main.humidity)
    }

}