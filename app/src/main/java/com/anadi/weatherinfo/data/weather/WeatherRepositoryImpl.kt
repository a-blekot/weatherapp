package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherResponse
import com.anadi.weatherinfo.data.network.openweather.OpenWeatherApi
import com.anadi.weatherinfo.data.network.openweather.OpenWeatherResponse
import com.anadi.weatherinfo.data.network.weatherbit.WeatherbitApi
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import java.util.*
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val weatherApi: WeatherApi,
        private val weatherDao: WeatherDao,
        private val locationRepository: LocationRepository) : WeatherRepository {

    override suspend fun fetchAll(): List<Weather> {
        return weatherDao.fetchAll()
    }

    override suspend fun fetch(city: String, country: String): Weather? {
        val location = locationRepository.fetch(city, country) ?: return null
        val weather = weatherDao.fetchAllForLocation(location.locationId!!).getOrNull(0)

        if (weather != null && dataIsFresh(weather.downloadTimestamp)) {
            return weather
        }

        return update(location, weather)
    }

    override suspend fun fetchAllForLocation(id: Long): List<Weather> {
        return weatherDao.fetchAllForLocation(id)
    }

    override suspend fun fetchAllForProvider(id: Long): List<Weather> {
        return weatherDao.fetchAllForProvider(id)
    }

    override suspend fun update(location: Location) {
        val weather = weatherDao.fetchAllForLocation(location.locationId!!).getOrNull(0)
        update(location, weather)
    }

    private suspend fun update(location: Location, weather: Weather?): Weather {
        val response = getWeatherResponse(location)
        return addOrUpdate(location.locationId!!, weather, response)
    }

    override suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }

    private fun dataIsFresh(timestamp: Long): Boolean {
        return timestamp.compareTo(System.currentTimeMillis() - ONE_HOUR) > 0
    }

    private suspend fun getWeatherResponse(location: Location): WeatherResponse {
        return weatherApi.getWeather(location)
    }

    private suspend fun addOrUpdate(locationId: Long, weather: Weather?, weatherResponse: WeatherResponse): Weather {
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