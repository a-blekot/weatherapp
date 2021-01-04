package com.anadi.weatherinfo.ui.locations

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anadi.weatherinfo.repository.LocationInfo
import com.anadi.weatherinfo.repository.LocationsCash
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class LocationsViewModel @Inject constructor(private val locationsCash: LocationsCash) : ViewModel(), Observer {
    private val locations: MutableLiveData<ArrayList<LocationInfo>> = MutableLiveData(arrayListOf())

    val locationsNotifier: LiveData<ArrayList<LocationInfo>>
        get() = locations

    fun loadLocations(context: Context) {
        locationsCash.loadLocations(context)
    }

    fun subscribe() {
        locationsCash.addObserver(this)
    }

    fun deleteLocation(locationInfo: LocationInfo) {
        locationsCash.deleteLocation(locationInfo)
    }

    override fun update(o: Observable, arg: Any?) {
        locations.postValue(locationsCash.locations)
    }

    override fun onCleared() {
        locationsCash.deleteObserver(this)
        super.onCleared()
        Timber.i("LocationsViewModel destroyed!")
    }
}