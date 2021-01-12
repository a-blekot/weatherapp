package com.anadi.weatherinfo.view.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.location.UpdateLocationUseCase
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.WeatherException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class DetailsViewModel @Inject constructor(private val updateLocationUseCase: UpdateLocationUseCase,
                                           private val locationRepository: LocationRepository) : ViewModel() {
    var locationId: Int = 0
    var providerId: Int = 0

    private val details: MutableLiveData<Resource<LocationWithWeathers>> = MutableLiveData()
    val detailsNotifier: LiveData<Resource<LocationWithWeathers>>
        get() = details

    fun fetch() {
        viewModelScope.launch {
            details.value = Resource.loading()

            try {
                val data = locationRepository.fetchWithWeathers(locationId)
                details.postValue(Resource.success(data = data))
            } catch (e: WeatherException) {
                Timber.e(e)
                details.postValue(Resource.error(R.string.on_error_update))
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            details.value = Resource.loading()

            try {

                val time = measureTimeMillis {
                    updateLocationUseCase.build(UpdateLocationUseCase.Params(locationId))
                }

                Timber.d("Update took $time ms")

                val data = locationRepository.fetchWithWeathers(locationId)

                details.postValue(Resource.success(data = data))

            } catch (e: WeatherException) {
                Timber.e(e)
                details.postValue(Resource.error(R.string.on_error_update))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("DetailsViewModel destroyed!")
    }
}
