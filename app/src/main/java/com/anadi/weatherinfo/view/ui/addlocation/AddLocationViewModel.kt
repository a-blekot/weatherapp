package com.anadi.weatherinfo.view.ui.addlocation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.LocationsCash
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.WeatherException
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(private val locationCash: LocationsCash,
                                               private val locationsProvider: LocationsProvider) : ViewModel() {
    private val exec = Executors.newSingleThreadExecutor()

    private val status: MutableLiveData<Resource<Any>> = MutableLiveData()
    val statusNotifier: LiveData<Resource<Any>>
        get() = status

    private val cities: MutableLiveData<List<String>> = MutableLiveData()
    val citiesNotifier: LiveData<List<String>>
        get() = cities

    fun addLocation(cityName: String, countryName: String) {
        Timber.i("addLocation $cityName $countryName")
        status.value = Resource.loading()

        if (TextUtils.isEmpty(cityName) || TextUtils.isEmpty(countryName) || cityName.equals(
                        "Select Item", ignoreCase = true) || countryName
                        .equals("Select Item", ignoreCase = true)) {
            Timber.d("selectedCity: " + cityName + "selectedCountry: " + countryName)
            status.value = Resource.error(R.string.on_error_select_city)
            return
        }

        exec.execute {
            try {
                locationCash.add(cityName, countryName)
                status.postValue(Resource.success(null))
            } catch (e: WeatherException) {
                Timber.e(e)
                status.postValue(Resource.error(R.string.on_error_load_location))
            }
        }
    }

    fun onCountrySelected(countryName: String) {
        cities.value = locationsProvider.getCityNames(countryName)
    }

    val countryNames: List<String>
        get() = locationsProvider.countryNames
}