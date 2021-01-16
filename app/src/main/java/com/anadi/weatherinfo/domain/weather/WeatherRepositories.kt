package com.anadi.weatherinfo.domain.weather

import com.anadi.weatherinfo.data.db.location.Location

interface WeatherRepositories {
    suspend fun fetch(location: Location)

    suspend fun update(location: Location)
}
