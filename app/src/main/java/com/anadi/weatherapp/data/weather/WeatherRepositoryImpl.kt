package com.anadi.weatherapp.data.weather

import android.util.Log
import com.anadi.weatherapp.data.db.location.Coord
import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.db.weather.WeatherDao
import com.anadi.weatherapp.data.network.WeatherApi
import com.anadi.weatherapp.data.network.WeatherResponse
import com.anadi.weatherapp.data.network.state.NetworkMonitor
import com.anadi.weatherapp.domain.weather.WeatherRepository
import com.anadi.firebase_database_coroutines.readValue
import com.google.firebase.database.*
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("TooManyFunctions")
class WeatherRepositoryImpl @Inject constructor(
        private val fbLocations: DatabaseReference,
        private val networkMonitor: NetworkMonitor,
        private val weatherApi: WeatherApi,
        private val weatherDao: WeatherDao
) : WeatherRepository {

    private val backgroundJob by lazy { SupervisorJob() }
    private val backgroundScope by lazy { CoroutineScope(Dispatchers.IO + backgroundJob) }

    private val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e("We are in WeatherRepositoryImpl coroutine exception handler")
        Timber.e(exception)
    }

    override suspend fun fetchAll(): List<Weather> {
        return weatherDao.fetchAll()
    }

    override suspend fun fetch(location: Location): Weather? {
        val weather = weatherDao.fetch(location.id, weatherApi.provider.code)

        fbLocations
                .child("${location.googlePlaceId}/weather")
                .addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Timber.d("onDataChange ${location.googlePlaceId}/weather")
                        Timber.d("snapshot.exists() = ${snapshot.exists()}")
                        Timber.d("snapshot = ${snapshot.value}")

                        Timber.d("snapshot.hasChild(${weatherApi.provider}) = ${snapshot.hasChild(weatherApi.provider.toString())}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Timber.d("onCancelled")
                        Timber.d("$error")
                    }
                })

        return if (weather != null && dataIsFresh(weather.downloadTimestamp)) {
            weather
        } else {
            fbLocations.fetch(location) ?: update(location, weather)
        }
    }

    private suspend fun DatabaseReference.fetch(location: Location): Weather? {
        if (networkMonitor.offline) {
            return null
        }

        Timber.d("Try to open child: ${location.googlePlaceId}/weather/timestamp")
        var timestamp: Long? = null
        try {
            timestamp = child("${location.googlePlaceId}/weather/timestamp").readValue() as Long
        } catch (e: Exception) {
            Timber.e(e)
        }

        var rawData: Any? = null
        timestamp?.let {
            Timber.d("timestamp is not null!")
            if (dataIsFresh(it)) {
                Timber.d("data is fresh!")
                Timber.d("Try to open child: ${location.googlePlaceId}/weather/${weatherApi.provider}")
                try {
                    rawData = child("${location.googlePlaceId}/weather/${weatherApi.provider}").readValue() as Any
                } catch (e: Exception) {
                    Timber.d("can't fetch provider ${weatherApi.provider}")
                    Timber.e(e)
                }

                Timber.d("and weather is loaded $rawData")
            }
        }

        Timber.d("return raw data = $rawData ")
        return rawData?.let { addOrUpdateFromFb(location, it) }
    }

    override suspend fun fetchAllForLocation(id: Int): List<Weather> {
        return weatherDao.fetchAllForLocation(id)
    }

    override suspend fun fetchAllForProvider(id: Int): List<Weather> {
        return weatherDao.fetchAllForProvider(id)
    }

    override suspend fun update(location: Location): Unit = withContext (Dispatchers.IO) {
        val weather = weatherDao.fetch(location.id, weatherApi.provider.code)
        update(location, weather)
    }

    override suspend fun checkForUpdate() {
        weatherDao.fetchAll()
    }

    private suspend fun update(location: Location, weather: Weather?): Weather? {
        if (networkMonitor.offline) {
            return weather
        }

        val response = getWeatherResponse(location.coord)
        Timber.d("Weather loaded from ${weatherApi.provider.providerName}")

        return addOrUpdate(location, weather, response)
    }

    override suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }

    private fun dataIsFresh(timestamp: Long): Boolean {
        val current = System.currentTimeMillis()
        return timestamp > current - TimeUnit.HOURS.toMillis(2)
    }

    private suspend fun getWeatherResponse(coord: Coord): WeatherResponse {
        return weatherApi.getWeather(coord)
    }

    private suspend fun addOrUpdate(location: Location, weather: Weather?, weatherResponse: WeatherResponse): Weather {
        val result: Weather
        if (weather == null) {
            result = WeatherMapper.convert(location.id, weatherResponse)
            weatherDao.insert(result)
        } else {
            result = WeatherMapper.update(weather, weatherResponse)
            weatherDao.update(result)
        }

        fbLocations.child(location.googlePlaceId)
                .child("weather")
                .child(weatherApi.provider.toString())
                .setValue(result)

        fbLocations.child(location.googlePlaceId)
                .child("weather")
                .child("timestamp")
                .setValue(result.downloadTimestamp)

        return result
    }

    private suspend fun addOrUpdateFromFb(location: Location, rawData: Any): Weather? {
        val result: Weather
        val oldWeather = weatherDao.fetch(location.id, weatherApi.provider.code)

        try {
            if (oldWeather == null) {
                result = WeatherMapper.convert(rawData)
                weatherDao.insert(result)
            } else {
                result = WeatherMapper.update(oldWeather, rawData)
                weatherDao.update(result)
            }
        } catch (e: Exception) {
            Timber.e("Could not convert to Weather: rawData")
            Timber.e(e)
            return null
        }

        return result
    }
}
