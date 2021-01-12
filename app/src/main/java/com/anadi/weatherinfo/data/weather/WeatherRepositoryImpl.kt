package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.db.weather.WeatherDao
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherResponse
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
        private val weatherApi: WeatherApi,
        private val weatherDao: WeatherDao,
        private val locationRepository: LocationRepository) : WeatherRepository {

    override suspend fun fetchAll(): List<Weather> {
        return weatherDao.fetchAll()
    }

    override suspend fun fetch(locationId: Int): Weather? {
        val location = locationRepository.fetch(locationId) ?: return null
        val weather = weatherDao.fetch(location.id, weatherApi.provider.code)

        if (weather != null && dataIsFresh(weather.downloadTimestamp)) {
            return weather
        }

        return update(location, weather)
    }

    override suspend fun fetchAllForLocation(id: Int): List<Weather> {
        return weatherDao.fetchAllForLocation(id)
    }

    override suspend fun fetchAllForProvider(id: Int): List<Weather> {
        return weatherDao.fetchAllForProvider(id)
    }

    override suspend fun update(location: Location) {
        val weather = weatherDao.fetch(location.id, weatherApi.provider.code)
        update(location, weather)
    }

    private suspend fun update(location: Location, weather: Weather?): Weather {
        val response = getWeatherResponse(location.coord)
        return addOrUpdate(location.id, weather, response)
    }

    override suspend fun delete(weather: Weather) {
        weatherDao.delete(weather)
    }

    private fun dataIsFresh(timestamp: Long): Boolean {
        return timestamp > System.currentTimeMillis() - ONE_HOUR
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