package com.anadi.weatherinfo.data.network.weatherbit

import com.google.gson.annotations.SerializedName

data class WeatherbitResponse(
        @SerializedName("count")
        val count: Int,
        @SerializedName("data")
        val data: List<WeatherbitData>
)

data class WeatherbitData(

        @SerializedName("weather")
        val weather: Weather,

        @SerializedName("temp")
        val temp: Float,
        @SerializedName("app_temp")
        val tempFeelsLike: Float,

        @SerializedName("wind_spd")
        val windSpeed: Float,
        @SerializedName("wind_dir")
        val windDegree: Int, // 291 deg == WNW -> counter clockwise?

        @SerializedName("pres")
        val pressure: Float,
        @SerializedName("rh")
        val humidity: Float, // Relative humidity (%)

        @SerializedName("clouds")
        val clouds: Int, // %

        @SerializedName("aqi")
        val airQualityIndex: Int,

        @SerializedName("uv")
        val uv: Float, // UV Index (0-11+)

        @SerializedName("solar_rad")
        val solarRad: Float,

        @SerializedName("ts")
        val dataCalcTimestamp: Long, // Last observation time (Unix timestamp in seconds)

        @SerializedName("ob_time")
        val dataCalcDateTime: String // Last observation time (YYYY-MM-DD HH:MM)
)

data class Weather(
        @SerializedName("code")
        val code: Int
)
