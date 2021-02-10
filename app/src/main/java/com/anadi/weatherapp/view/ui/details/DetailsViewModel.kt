package com.anadi.weatherapp.view.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.domain.location.LocationRepository
import com.anadi.weatherapp.domain.location.UpdateLocationUseCase
import com.anadi.weatherapp.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class DetailsViewModel @Inject constructor(
        private val updateLocationUseCase: UpdateLocationUseCase, private val locationRepository: LocationRepository
) : ViewModel() {
    var locationId: Int = 0
    var providerId: Int = 0

    private val details: MutableLiveData<Resource<LocationWithWeathers>> = MutableLiveData()
    val detailsNotifier: LiveData<Resource<LocationWithWeathers>>
        get() = details

    fun fetch() {
        details.value = Resource.loading()
        viewModelScope.launch {
            val data = locationRepository.fetchWithWeathers(locationId)
            details.postValue(Resource.success(data = data))
        }
    }

    fun update() {
        details.value = Resource.loading()
        viewModelScope.launch {
            measureTimeMillis("Update locationId=$locationId") {
                updateLocationUseCase.build(UpdateLocationUseCase.Params(locationId))

                val data = locationRepository.fetchWithWeathers(locationId)
                details.postValue(Resource.success(data = data))
            }
        }
    }
}

inline fun measureTimeMillis(logMessage: String, function: () -> Unit) {
    val time = measureTimeMillis {
        function.invoke()
    }

    Timber.d( "$logMessage time = $time")
}
