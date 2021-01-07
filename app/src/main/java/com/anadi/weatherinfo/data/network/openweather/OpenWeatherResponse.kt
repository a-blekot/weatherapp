package com.anadi.weatherinfo.data.network.openweather

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponse(
        @SerializedName("coord")
        val coord: Coord,
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

data class Coord(
        @SerializedName("lon")
        val lon: Float,
        @SerializedName("lat")
        val lat: Float
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
        val deg: Int)

data class Clouds(
        @SerializedName("all")
        val all: Int) // %

//enum class State(val id: Int, val mainDescription: String, val detailedDescription: String, val iconId: Int) {
//    NONE(0, "None", "None", -1),
//    CLEAR_SKY(800, "Clear", "Clear sky", 0),
//    CLOUDS(801, "Clouds", "few clouds: 11-25%", 1);
//}
