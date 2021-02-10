package com.anadi.weatherapp.data.weather

interface WeatherCodes {
    fun from(code: Int): WeatherCode
}
