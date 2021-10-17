package com.anadi.weatherapp.view.ui.locations

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.anadi.weatherapp.data.weather.WeatherCodes
import com.anadi.weatherapp.databinding.LocationsFragmentBinding
import com.anadi.weatherapp.utils.DateFormats
import com.anadi.weatherapp.view.ui.BaseFragment
import com.anadi.weatherapp.view.ui.notification.WeatherNotificationManager
import es.dmoral.toasty.Toasty
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class LocationsFragment : BaseFragment(R.layout.locations_fragment), LocationAdapter.Listener {
    private var binding: LocationsFragmentBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var weatherCodes: WeatherCodes

    @Inject
    lateinit var notificationManager: WeatherNotificationManager

    private lateinit var viewModel: LocationsViewModel

    private lateinit var adapter: LocationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            LocationsFragmentBinding.inflate(inflater, container, false).apply { binding = this }.root

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the viewModel
        viewModel = ViewModelProvider(this, viewModelFactory).get(LocationsViewModel::class.java)
        viewModel.updateSuntime()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationAdapter(this, weatherCodes)
        binding?.recyclerView?.apply {
            adapter = this@LocationsFragment.adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.isConnected.observe(viewLifecycleOwner) { onConnectionChanged(it) }
        viewModel.locationsNotifier.observe(viewLifecycleOwner) { adapter.dataset = it }

        binding?.addLocationButton?.setOnClickListener {
            startActivityForResult(viewModel.placesIntent, AUTOCOMPLETE_REQUEST_CODE)
            // findNavController().navigate(LocationsFragmentDirections.actionLocationsToAddLocation())
        }

        binding?.notification?.setOnClickListener {
            notificationManager.sendNotification("MainActivity", "time = ${DateFormats.defaultTime.print(DateTime.now())}")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLocations()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//
////            val place = Autocomplete.getPlaceFromIntent(data)
////            viewModel.addLocation(place)
//
//        } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
//            val status = Autocomplete.getStatusFromIntent(data)
//            Toasty.error(requireContext(), "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
//            Timber.e(status.statusMessage)
//        }
    }

    private fun onConnectionChanged(isConnected: Boolean) {
        binding?.addLocationButton?.isVisible = isConnected
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
