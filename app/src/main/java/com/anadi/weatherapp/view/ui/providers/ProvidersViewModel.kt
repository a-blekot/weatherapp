package com.anadi.weatherapp.view.ui.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.weather.WeatherMapper
import com.anadi.weatherapp.domain.location.LocationRepository
import com.anadi.weatherapp.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProvidersViewModel @Inject constructor(private val locationRepository: LocationRepository) : ViewModel() {
    var id: Int = 0

    private val _details: MutableLiveData<Resource<LocationWithWeathers>> = MutableLiveData()
    val details: LiveData<Resource<LocationWithWeathers>>
        get() = _details

    private val _mergedWeather: MutableLiveData<Weather> = MutableLiveData()
    val mergedWeather: LiveData<Weather>
            get() = _mergedWeather

    fun fetch() {
        _details.value = Resource.loading()
        viewModelScope.launch {
            val data = locationRepository.fetchWithWeathers(id)
            _details.postValue(Resource.success(data = data))

            data?.weathers?.let {
                val weather = WeatherMapper.merge(it)
                if (weather != null) {
                    _mergedWeather.postValue(weather)
                }
            }
        }
    }
}
