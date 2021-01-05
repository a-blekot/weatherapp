package com.anadi.weatherinfo.view.ui.locations

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.databinding.LocationsFragmentBinding
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.LocationInfo
import com.anadi.weatherinfo.view.ui.BaseFragment
import javax.inject.Inject

class LocationsFragment : BaseFragment(R.layout.locations_fragment), LocationAdapter.OnLocationSelectedListener {
    private val binding: LocationsFragmentBinding by viewBinding()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocationsViewModel

    private lateinit var adapter: LocationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the viewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationsViewModel::class.java)

        IconMap.init(requireContext())
        viewModel.loadLocations(requireContext())
        adapter = LocationAdapter(this)
        binding.recyclerView.adapter = adapter

        viewModel.subscribe()
        viewModel.locationsNotifier.observe(viewLifecycleOwner, Observer { adapter.locations = it })

        binding.addLocationButton.setOnClickListener {
            findNavController().navigate(LocationsFragmentDirections.actionLocationsToAddLocation())
        }
    }

    override fun onSelected(locationInfo: LocationInfo) {
        val action = LocationsFragmentDirections.actionLocationsToDetails()
        action.locationId = locationInfo.id
        findNavController().navigate(action)
    }

    override fun onMenuAction(locationInfo: LocationInfo, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_context_delete -> {
                viewModel.deleteLocation(locationInfo)
            }
            R.id.menu_context_favorite -> {
            }
        }
    }
}