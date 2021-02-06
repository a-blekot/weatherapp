package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherResponse
import com.anadi.weatherinfo.data.network.state.NetworkMonitor
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@Suppress("TooManyFunctions")
class WeatherRepositoryImpl @Inject constructor(
        private val networkMonitor: NetworkMonitor,
        private val weatherApi: WeatherApi,
        private val weatherDao: WeatherDao
) : WeatherRepository {

    override suspend fun fetchAll(): List<Weather> {
        return weatherDao.fetchAll()
    }

    override suspend fun fetch(location: Location): Weather? {
        val weather = weatherDao.fetch(location.id, weatherApi.provider.code)

        return if (weather != null && dataIsFresh(weather.downloadTimestamp)) {
            weather
        } else {
            update(location, weather)
        }
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

        return addOrUpdate(location.id, weather, response)
    }

    override suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }

    private fun dataIsFresh(timestamp: Long): Boolean {
        val current = System.currentTimeMillis()
        return timestamp > current - ONE_HOUR
    }

    private suspend fun getWeatherResponse(coord: Coord): WeatherResponse {
        return weatherApi.getWeather(coord)
    }

    private suspend fun addOrUpdate(locationId: Int, weather: Weather?, weatherResponse: WeatherResponse): Weather {
        val result: Weather
        if (weather == null) {
            result = WeatherMapper.convert(locationId, weatherResponse)
            weatherDao.insert(result)
        } else {
            result = WeatherMapper.update(weather, weatherResponse)
            weatherDao.update(result)
        }

        return result
    }

    companion object {
        private const val ONE_HOUR: Long = 1000 * 60 * 60
    }
}
