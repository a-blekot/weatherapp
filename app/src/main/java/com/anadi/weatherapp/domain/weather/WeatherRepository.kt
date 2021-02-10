package com.anadi.weatherapp.domain.weather

import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.weather.Weather

interface WeatherRepository {

    suspend fun fetchAll(): List<Weather>

    suspend fun fetch(location: Location): Weather?

    suspend fun fetchAllForLocation(id: Int): List<Weather>

    suspend fun fetchAllForProvider(id: Int): List<Weather>

    suspend fun update(location: Location)

    suspend fun checkForUpdate()

    suspend fun delete(weather: Weather)
}
