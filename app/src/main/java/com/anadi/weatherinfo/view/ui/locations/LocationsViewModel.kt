package com.anadi.weatherinfo.view.ui.locations

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.network.state.NetworkMonitor
import com.anadi.weatherinfo.domain.location.AddLocationUseCase
import com.anadi.weatherinfo.domain.location.CheckUpdatesAllLocationsUseCase
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.places.PlacesWrapper
import com.google.android.libraries.places.api.model.Place
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MILLIS_IN_MINUTE = 60 * 1000

class LocationsViewModel @Inject constructor(
        private val networkMonitor: NetworkMonitor,
        private val addLocationUseCase: AddLocationUseCase,
        private val placesWrapper: PlacesWrapper,
        private val checkUpdatesAllLocationsUseCase: CheckUpdatesAllLocationsUseCase,
        private val locationRepository: LocationRepository
) : ViewModel() {

    private val locations: MutableLiveData<List<LocationWithWeathers>> = MutableLiveData(arrayListOf())
    val locationsNotifier: LiveData<List<LocationWithWeathers>>
        get() = locations

    val isConnected: LiveData<Boolean>
        get() = networkMonitor.isConnected

    fun updateSuntime() {
        viewModelScope.launch {
            locationRepository.updateSuntime()
        }
    }

    fun updateLocations() {
        viewModelScope.launch {
            checkUpdatesAllLocationsUseCase.build(null)
        }
    }

    fun loadLocations() {
        viewModelScope.launch {
            val data = locationRepository.fetchAllWithWeathers()
            locations.postValue(data)
        }
    }

    val placesIntent: Intent
        get() = placesWrapper.createActivityIntent()

    fun addLocation(place: Place) {
        viewModelScope.launch {
            addLocationUseCase.build(
                    AddLocationUseCase.Params(
                            name = place.name!!,
                            address = place.address!!,
                            coord = Coord.from(place.latLng!!),
                            utcOffsetMillis = place.utcOffsetMinutes!! * MILLIS_IN_MINUTE
                    )
            )
            update()
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.delete(location)
            update()
        }
    }

    private suspend fun update() {
        val data = locationRepository.fetchAllWithWeathers()
        locations.postValue(data)
    }
}
