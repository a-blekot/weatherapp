package com.anadi.weatherinfo.view.ui.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.domain.location.LocationRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LocationsViewModel @Inject constructor(private val locationRepository: LocationRepository) : ViewModel() {

    private val locations: MutableLiveData<List<LocationWithWeathers>> = MutableLiveData(arrayListOf())
    val locationsNotifier: LiveData<List<LocationWithWeathers>>
        get() = locations

    fun loadLocations() {
        viewModelScope.launch {
            val data = locationRepository.fetchAllWithWeathers()
            locations.postValue(data)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.delete(location)

            val data = locationRepository.fetchAllWithWeathers()
            locations.postValue(data)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("LocationsViewModel destroyed!")
    }
}