package com.anadi.weatherinfo.domain.weather

import com.anadi.weatherinfo.data.db.location.Location

interface WeatherRepositories {
    suspend fun fetch(city: String, country: String)

    suspend fun update(location: Location)
}