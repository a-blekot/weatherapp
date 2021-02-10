package com.anadi.weatherapp.domain.weather

import com.anadi.weatherapp.data.db.location.Location

interface WeatherRepositories {
    suspend fun fetch(location: Location)

    suspend fun update(location: Location)
}
