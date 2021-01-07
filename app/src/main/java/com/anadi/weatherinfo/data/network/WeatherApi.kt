package com.anadi.weatherinfo.data.network

import com.anadi.weatherinfo.data.db.location.Location

interface WeatherApi {
    val provider: WeatherProvider

    suspend fun getWeather(location: Location): WeatherResponse
}