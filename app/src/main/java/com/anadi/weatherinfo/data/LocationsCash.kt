package com.anadi.weatherinfo.data

import android.content.Context
import com.anadi.weatherinfo.data.data.WeatherInfo
import com.anadi.weatherinfo.view.ui.addlocation.LocationsProvider
import com.anadi.weatherinfo.utils.WeatherException
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.jvm.Throws

class LocationsCash @Inject constructor(private val locationsProvider: LocationsProvider,
                                        private val infoLoader: InfoLoader) :
        Observable() {

    var locations = ArrayList<LocationInfo>()
        private set

    fun add(cityName: String, countryName: String) {
        val country = locationsProvider.getCountryByName(countryName)

        val locationInfo = getCashedInfo(cityName, country)
        if (locationInfo != null) {
            Timber.d("City already loaded: %s", cityName)
            return
        }

        load(cityName, country)
    }

    @Throws(WeatherException::class)
    private fun load(cityName: String, country: Country) {
        val weatherInfo = infoLoader.load(cityName, country)
        val locationInfo = LocationInfo(cityName, country)
        locationInfo.info = weatherInfo
        locations.add(locationInfo)
        setChanged()
        notifyObservers()
    }

    fun loadLocations(context: Context) {
        Timber.i("loadLocations called")
        locationsProvider.loadLocations(context)
    }

    fun deleteLocation(locationInfo: LocationInfo) {
        val removed = locations.remove(locationInfo)
        if (removed) {
            setChanged()
            notifyObservers()
        }
    }

    fun getInfo(id: Int): WeatherInfo? {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                return locationInfo.info
            }
        }
        return null
    }

    fun update(id: Int) {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                tryUpdate(locationInfo)
            }
        }
    }

    @Throws(WeatherException::class)
    private fun tryUpdate(locationInfo: LocationInfo) {
        val weatherInfo = infoLoader.load(locationInfo.cityName, locationInfo.country)
        locationInfo.info = weatherInfo
        setChanged()
        notifyObservers(locationInfo)
    }

    fun needUpdate(id: Int): Boolean {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                return dataIsOld(locationInfo.info.dt)
            }
        }
        Timber.d("There is no data at all for id = %d", id)
        return false
    }

    private fun getCashedInfo(cityName: String?, country: Country): LocationInfo? {
        for (locationInfo in locations) {
            if (locationInfo.cityName == cityName && locationInfo.country == country) {
                return locationInfo
            }
        }
        return null
    }

    private fun dataIsOld(timestamp: Int): Boolean {
        return System.currentTimeMillis() - timestamp > TIME_TO_UPDATE
    }

    companion object {
        private const val APP_DATA_FILE = "weatherinfo.db"
        private const val APP_DATA_DIR = "db"
        private const val TIME_TO_UPDATE = 1000 * 60 * 60 // data is considered to be "fresh" during one hour
    }
}