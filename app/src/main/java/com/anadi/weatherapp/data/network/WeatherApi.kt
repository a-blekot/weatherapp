package com.anadi.weatherapp.data.network

import com.anadi.weatherapp.data.db.location.Coord

interface WeatherApi {
    val provider: WeatherProvider

    suspend fun getWeather(coord: Coord): WeatherResponse
}
