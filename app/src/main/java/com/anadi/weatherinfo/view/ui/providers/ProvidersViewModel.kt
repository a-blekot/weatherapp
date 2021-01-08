package com.anadi.weatherinfo.view.ui.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.weather.WeatherMapper
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.WeatherException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProvidersViewModel @Inject constructor(private val locationRepository: LocationRepository) : ViewModel() {
    var id: Int = 0

    private val _details: MutableLiveData<Resource<LocationWithWeathers>> = MutableLiveData()
    val details : LiveData<Resource<LocationWithWeathers>>
        get() = _details

    private val _mergedWeather: MutableLiveData<Weather> = MutableLiveData()
    val mergedWeather: LiveData<Weather>
        get() = _mergedWeather

    fun fetch() {
        viewModelScope.launch {
            _details.value = Resource.loading()

            try {
                val data = locationRepository.fetchWithWeathers(id)

                _details.postValue(Resource.success(data = data))

                data?.weathers?.let {
                    val weather = WeatherMapper.merge(it)
                    if (weather != null) {
                        _mergedWeather.postValue(weather)
                    }
                }
            } catch (e: WeatherException) {
                Timber.e(e)
                _details.postValue(Resource.error(R.string.on_error_update))
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        Timber.i("DetailsViewModel destroyed!")
    }
}