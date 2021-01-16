package com.anadi.weatherinfo.data.network

data class WeatherResponse(
        val provider: WeatherProvider,

        val code: Int,

        val temp: Int, val tempFeelsLike: Int,

        val windSpeed: Int, val windDegree: Int,

        val pressure: Int, val humidity: Int, val clouds: Int, val dataCalcTimestamp: Long
)
