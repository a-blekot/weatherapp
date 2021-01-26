package com.anadi.weatherinfo.view.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.location.UpdateLocationUseCase
import com.anadi.weatherinfo.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
            updateLocationUseCase.build(UpdateLocationUseCase.Params(locationId))

            val data = locationRepository.fetchWithWeathers(locationId)
            details.postValue(Resource.success(data = data))
        }
    }
}
