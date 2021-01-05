package com.anadi.weatherinfo.data.data

data class WeatherInfo(var coord: Coord, var weather: List<Weather>,
                       var base: String,
                       var main: Main,
                       var visibility: Int = 0,
                       var wind: Wind,
                       var clouds: Clouds,
                       var dt: Int = 0, // Time of data calculation, unix, UTC
                       var sys: Sys,
                       var timezone: Int = 0, // Shift in seconds from UTC. Divide in 3600 to get +/- hours
                       var id: Int = 0, // City ID
                       var name: String, // City Name
                       var cod: Int = 0)

data class Coord(var lon: Float = 0f, var lat: Float = 0f)

data class Weather(var id: Int = 0,
                   var main: String, // Group of weather parameters (Rain, Snow, Extreme etc.)
                   var icon: String)

data class Main(var temp: Float = 0f,
                var pressure: Int = 0,
                var humidity: Int = 0)

data class Wind(var speed: Float = 0f, var deg: Int = 0)

data class Clouds(var all: Int = 0) // %

// Country code (GB, JP etc.))
data class Sys(var country: String)


//enum class State(val id: Int, val mainDescription: String, val detailedDescription: String, val iconId: Int) {
//    NONE(0, "None", "None", -1),
//    CLEAR_SKY(800, "Clear", "Clear sky", 0),
//    CLOUDS(801, "Clouds", "few clouds: 11-25%", 1);
//}
