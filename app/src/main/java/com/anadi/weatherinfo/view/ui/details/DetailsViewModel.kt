package com.anadi.weatherinfo.view.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.LocationInfo
import com.anadi.weatherinfo.data.LocationsCash
import com.anadi.weatherinfo.data.data.WeatherInfo
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.WeatherException
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val locationsCash: LocationsCash) : ViewModel(), Observer {
    var id: Int = 0

    private val details: MutableLiveData<Resource<WeatherInfo>> = MutableLiveData(Resource.success(locationsCash.getInfo(id)))
    val detailsNotifier: LiveData<Resource<WeatherInfo>>
        get() = details

    fun update() {
        if (!locationsCash.needUpdate(id)) {
            Timber.d("No need for update. Data is fresh id = %d", id)
            return
        }
        details.value = Resource.loading()
        Thread(Runnable {
            try {
                locationsCash.update(id)
            } catch (e: WeatherException) {
                Timber.e(e)
                details.postValue(Resource.error(R.string.on_error_update))
            }
        }).start()
    }

    fun subscribe() {
        locationsCash.addObserver(this)
    }

    override fun update(o: Observable, arg: Any?) {
        if (arg is LocationInfo && arg.id == id) {
            details.postValue(Resource.success(locationsCash.getInfo(id)))
        }
    }

    override fun onCleared() {
        locationsCash.deleteObserver(this)
        super.onCleared()
        Timber.i("LocationsViewModel destroyed!")
    }
}