package com.anadi.weatherinfo.data.weather

interface WeatherCodes {
    fun from(code: Int): WeatherCode
}
