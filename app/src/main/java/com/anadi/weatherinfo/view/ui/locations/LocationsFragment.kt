package com.anadi.weatherinfo.view.ui.locations

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.IconMap
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.databinding.LocationsFragmentBinding
import com.anadi.weatherinfo.view.ui.BaseFragment
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import es.dmoral.toasty.Toasty
import timber.log.Timber
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

        adapter = LocationAdapter(this)
        binding.recyclerView.adapter = adapter

        viewModel.locationsNotifier.observe(viewLifecycleOwner, Observer { adapter.dataset = it })

        binding.addLocationButton.setOnClickListener {
            startActivityForResult(viewModel.placesIntent, AUTOCOMPLETE_REQUEST_CODE)
            // findNavController().navigate(LocationsFragmentDirections.actionLocationsToAddLocation())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            val place = Autocomplete.getPlaceFromIntent(data)

            place.utcOffsetMinutes

            val coord = place.latLng!!

            Toasty.success(requireContext(), "Coordinates: $coord", Toast.LENGTH_LONG).show()
            Timber.i("Coordinates: $coord")

            viewModel.addLocation(place)

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
            val status = Autocomplete.getStatusFromIntent(data)
            Toasty.error(requireContext(), "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
            Timber.e(status.statusMessage)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLocations()
    }

    override fun onSelected(location: Location) {
        val action = LocationsFragmentDirections.actionLocationsToProviders()
        action.locationId = location.id
        findNavController().navigate(action)
    }

    override fun onMenuAction(location: Location, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_context_delete -> {
                viewModel.deleteLocation(location)
            }
            R.id.menu_context_favorite -> {
            }
        }
    }

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 4576
    }
}
