package com.anadi.weatherinfo.repository

import android.content.Context
import com.anadi.weatherinfo.repository.data.WeatherInfo
import com.anadi.weatherinfo.ui.addlocation.AddLocationContract
import com.anadi.weatherinfo.ui.addlocation.LocationsProvider
import com.anadi.weatherinfo.ui.details.DetailsContract
import com.anadi.weatherinfo.ui.mainactivity.MainActivityContract
import timber.log.Timber
import java.io.*
import java.util.*
import javax.inject.Inject

class LocationsCash @Inject constructor (private val locationsProvider: LocationsProvider,
                                         private val infoLoader: InfoLoader) :
        Observable(),
        AddLocationContract.Model,
        MainActivityContract.Model,
        DetailsContract.Model {

    override var locations = ArrayList<LocationInfo>()
        private set

    override fun add(cityName: String, countryName: String): Boolean {
        val country = locationsProvider.getCountryByName(countryName)

        val locationInfo = getCashedInfo(cityName, country)
        if (locationInfo != null) {
            Timber.d("City already loaded: %s", cityName)
            return true
        }
        return load(cityName, country)
    }

    override fun loadLocations(context: Context) {
        locationsProvider.loadLocations(context)
    }

    override fun saveData(context: Context) {
        val path = File(context.filesDir, APP_DATA_DIR)
        if (!path.exists()) {
            if (!path.mkdirs()) {
                Timber.d("Failed to create app db dir!")
                return
            }
        }
        val file = File(path, APP_DATA_FILE)
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    Timber.d("Failed to create app db file!")
                    return
                }
            } catch (e: IOException) {
                Timber.d("Failed to create app db file!")
                e.printStackTrace()
                return
            }
        }
        try {
            FileOutputStream(file).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(locations)
                    oos.flush()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun loadData(context: Context) {
        val path = File(context.filesDir, APP_DATA_DIR)
        if (!path.exists()) {
            Timber.d("No db dir!")
            return
        }
        val file = File(path, APP_DATA_FILE)
        if (!file.exists()) {
            Timber.d("No db file!")
            return
        }
        try {
            FileInputStream(file).use { fis -> ObjectInputStream(fis).use { ois -> locations = ois.readObject() as ArrayList<LocationInfo> } }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun deleteLocation(locationInfo: LocationInfo): Boolean {
        val result = locations.remove(locationInfo)
        if (result) {
            setChanged()
            notifyObservers()
        }
        return result
    }

    override fun getInfo(id: Int): WeatherInfo? {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                return locationInfo.info
            }
        }
        return null
    }

    override fun update(id: Int): Boolean {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                val weatherInfo = infoLoader.load(locationInfo.cityName, locationInfo.country)
                if (weatherInfo == null) {
                    Timber.d("No info loaded for location: $locationInfo")
                    return false
                }
                locationInfo.info = weatherInfo
                setChanged()
                notifyObservers(locationInfo)
                return true
            }
        }
        return false
    }

    override fun needUpdate(id: Int): Boolean {
        for (locationInfo in locations) {
            if (locationInfo.id == id) {
                return dataIsOld(locationInfo.info.dt)
            }
        }
        Timber.d("There is no data at all for id = %d", id)
        return false
    }

    private fun load(cityName: String?, country: Country): Boolean {
        val weatherInfo = infoLoader.load(cityName, country)
        if (weatherInfo == null) {
            Timber.d("No info loaded for location: %s, %s", cityName, country)
            return false
        }
        val locationInfo = LocationInfo(cityName, country)
        locationInfo.info = weatherInfo
        locations.add(locationInfo)
        setChanged()
        notifyObservers()
        return true
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