package com.anadi.weatherinfo.view.ui.providers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.databinding.ProvidersFragmentBinding
import com.anadi.weatherinfo.view.ui.BaseFragment
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.Status
import com.anadi.weatherinfo.view.ui.details.DetailsFragmentArgs
import com.anadi.weatherinfo.view.ui.locations.LocationsFragmentDirections
import es.dmoral.toasty.Toasty
import timber.log.Timber
import javax.inject.Inject

class ProvidersFragment : BaseFragment(R.layout.providers_fragment), ProvidersAdapter.Listener {

    private val binding: ProvidersFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProvidersViewModel

    private lateinit var adapter: ProvidersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProvidersViewModel::class.java)
        viewModel.id = DetailsFragmentArgs.fromBundle(requireArguments()).locationId

        adapter = ProvidersAdapter(this)
        binding.recyclerView.adapter = adapter

        viewModel.detailsNotifier.observe(viewLifecycleOwner, Observer { update(it) })

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
            if (data == null) Timber.d("LocationWithWeathers is null!")

            binding.noData.visibility = View.VISIBLE
            return
        }

        adapter.dataset = data.weathers
        binding.locationName.text = getString(R.string.location_name, data.location.city, data.location.country.name)
    }

    override fun onSelected(weather: Weather) {
        val action = ProvidersFragmentDirections.actionProvidersToDetails()
        action.locationId = weather.locationId
        action.providerId = weather.providerId
        findNavController().navigate(action)
    }
}