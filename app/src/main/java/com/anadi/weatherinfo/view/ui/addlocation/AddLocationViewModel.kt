package com.anadi.weatherinfo.view.ui.addlocation

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.domain.location.AddLocationUseCase
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.utils.Resource
import com.anadi.weatherinfo.utils.WeatherException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AddLocationViewModel @Inject constructor(private val addLocationUseCase: AddLocationUseCase,
                                               private val locationRepository: LocationRepository,
                                               private val locationsProvider: LocationsProvider) : ViewModel() {
    private val status: MutableLiveData<Resource<Any>> = MutableLiveData()
    val statusNotifier: LiveData<Resource<Any>>
        get() = status

    private val cities: MutableLiveData<List<String>> = MutableLiveData()
    val citiesNotifier: LiveData<List<String>>
        get() = cities

    fun addLocation(city: String, country: String) {
        Timber.d("addLocation $city $country")
        status.value = Resource.loading()

        if (wrongSelection(city) || wrongSelection(country)) {
            status.value = Resource.error(R.string.on_error_select_city)
            return
        }

        viewModelScope.launch {
            try {
                if (alreadyAdded(city, country)) {
                    status.postValue(Resource.success(R.string.add_location_already_added, null))
                } else {
                    addLocationUseCase.build(AddLocationUseCase.Params(city, country))
                    status.postValue(Resource.success(data = null))
                }
            } catch (e: WeatherException) {
                Timber.e(e)
                status.postValue(Resource.error(R.string.on_error_load_location))
            }
        }
    }

    private suspend fun alreadyAdded(city: String, country: String): Boolean {
        return locationRepository.fetch(city, country) != null
    }

    private fun wrongSelection(selection: String): Boolean {
        return TextUtils.isEmpty(selection) || selection.equals("Select Item", ignoreCase = true)
    }

    fun onCountrySelected(countryName: String) {
        cities.value = locationsProvider.getCityNames(countryName)
    }

    val countryNames: List<String>
        get() = locationsProvider.countryNames

    override fun onCleared() {
        super.onCleared()
        Timber.i("AddLocationViewModel destroyed!")
    }
}