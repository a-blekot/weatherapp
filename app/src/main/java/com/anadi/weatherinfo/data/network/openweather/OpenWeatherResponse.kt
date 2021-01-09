package com.anadi.weatherinfo.data.network.openweather

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
        @SerializedName("weather")
        val weather: List<WeatherMain>,
        @SerializedName("main")
        val main: Main,
        @SerializedName("wind")
        val wind: Wind,
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("dt")
        val dataCalcTimestamp: Long, // Last observation time (Unix timestamp)
)

data class WeatherMain(
        @SerializedName("id")
        val code: Int
)

data class Main(
        @SerializedName("temp")
        val temp: Float,
        @SerializedName("feels_like")
        val tempFeelsLike: Float,

        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("humidity")
        val humidity: Int // %
)

data class Wind(
        @SerializedName("speed")
        val speed: Float,
        @SerializedName("deg")
        val deg: Int
)

data class Clouds(
        @SerializedName("all")
        val all: Int // %
)
