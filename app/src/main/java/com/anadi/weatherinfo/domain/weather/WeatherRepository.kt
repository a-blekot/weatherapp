package com.anadi.weatherinfo.domain.weather

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.weather.Weather

interface WeatherRepository {

    suspend fun fetchAll(): List<Weather>

    suspend fun fetch(city: String, country: String): Weather?

    suspend fun fetch(locationId: Int, providerId: Int): Weather?

    suspend fun fetchAllForLocation(id: Int): List<Weather>

    suspend fun fetchAllForProvider(id: Int): List<Weather>

    suspend fun update(location: Location)

    suspend fun delete(weather: Weather)
}