package com.anadi.weatherinfo.data.network

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
        @SerializedName("coord")
        var coord: Coord,
        @SerializedName("weather")
        var weather: List<WeatherMain>,
        @SerializedName("base")
        var base: String,
        @SerializedName("main")
        var main: Main,
        @SerializedName("wind")
        var wind: Wind,
        @SerializedName("clouds")
        var clouds: Clouds,
        @SerializedName("sys")
        var sys: Sys,
        @SerializedName("timezone")
        var timezone: Int = 0, // Shift in seconds from UTC. Divide in 3600 to get +/- hours
        @SerializedName("id")
        var id: Int = 0, // City ID
        @SerializedName("name")
        var name: String, // City Name
        @SerializedName("cod")
        var cod: Int = 0)

data class Coord(
        @SerializedName("lon")
        var lon: Float = 0f,
        @SerializedName("lat")
        var lat: Float = 0f)

data class WeatherMain(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("main")
        var main: String, // Group of weather parameters (Rain, Snow, Extreme etc.)
        @SerializedName("icon")
        var icon: String)

data class Main(
        @SerializedName("temp")
        var temp: Float = 0f,
        @SerializedName("pressure")
        var pressure: Int = 0,
        @SerializedName("humidity")
        var humidity: Int = 0)

data class Wind(
        @SerializedName("speed")
        var speed: Float = 0f,
        @SerializedName("deg")
        var deg: Int = 0)

data class Clouds(
        @SerializedName("all")
        var all: Int = 0) // %

// Country code (GB, JP etc.))
data class Sys(
        @SerializedName("country")
        var country: String)


//enum class State(val id: Int, val mainDescription: String, val detailedDescription: String, val iconId: Int) {
//    NONE(0, "None", "None", -1),
//    CLEAR_SKY(800, "Clear", "Clear sky", 0),
//    CLOUDS(801, "Clouds", "few clouds: 11-25%", 1);
//}
