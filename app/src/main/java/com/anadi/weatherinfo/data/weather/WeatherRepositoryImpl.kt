package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.LocationDao
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.network.WeatherApi
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

        var weather = weatherDao.fetchAllForLocation(location.locationId!!).getOrNull(0)
        if (weather != null && dataIsFresh(weather.lastUpdate)) {
            return weather
        }

        val locationName = "${location.city},${location.country.code.toLowerCase(Locale.ROOT)}"

//        Locale.ENGLISH.displayName.toLowerCase(Locale.ROOT)
        val weatherResponse = weatherApi.getWeather(locationName, API_KEY, UNITS_METRIC, LANG_EN)

        if (weather == null) {
            weather = WeatherMapper.convert(weatherResponse)
            weatherDao.insert(weather)
        } else {
            weather = WeatherMapper.update(weather, weatherResponse)
            weatherDao.update(weather)
        }

        weather = WeatherMapper.convert(weatherResponse)

        weatherDao.update(weather)
        return weather
    }

    override suspend fun fetchAllForLocation(id: Long): List<Weather> {
        return weatherDao.fetchAllForLocation(id)
    }

    override suspend fun fetchAllForProvider(id: Long): List<Weather> {
        return weatherDao.fetchAllForProvider(id)
    }

    override suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }

    private fun dataIsFresh(timestamp: Long): Boolean {
        return timestamp.compareTo(System.currentTimeMillis() - ONE_HOUR) > 0
    }

    companion object {
        private const val ONE_HOUR: Long = 1000 * 60 * 60
        private const val API_KEY = "f9dee5683fdf51c7b611df7f57f26926"
        private const val UNITS_METRIC = "metric"
        private const val LANG_EN = "en"
    }
}