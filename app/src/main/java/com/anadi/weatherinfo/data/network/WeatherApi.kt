package com.anadi.weatherinfo.data.network

import com.anadi.weatherinfo.data.db.location.Coord

interface WeatherApi {
    val provider: WeatherProvider

    suspend fun getWeather(coord: Coord): WeatherResponse
}