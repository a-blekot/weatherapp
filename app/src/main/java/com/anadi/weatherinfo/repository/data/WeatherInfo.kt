package com.anadi.weatherinfo.repository.data

data class WeatherInfo(var coord: Coord, var weather: List<Weather>,
                       var base: String,
                       var main: Main,
                       var visibility: Int = 0,
                       var wind: Wind,
                       var clouds: Clouds,
                       var dt: Int = 0, // Time of data calculation, unix, UTC
                       var sys: Sys,
                       var timezone: Int = 0, // Shift in seconds from UTC. Devide in 3600 to get +/- hours
                       var id: Int = 0, // City ID
                       var name: String, // City Name
                       var cod: Int = 0) {

    companion object {
        val EMPTY = WeatherInfo (Coord(0F, 0F),
        listOf(Weather(main = "", description = "", icon = "")),
        base = "",
        main = Main(),
        wind = Wind(),
        clouds = Clouds(),
        dt = 0,
        sys = Sys(country = ""),
        name = "")
    }
}


//enum class State(val id: Int, val mainDescription: String, val detailedDescription: String, val iconId: Int) {
//    NONE(0, "None", "None", -1),
//    CLEAR_SKY(800, "Clear", "Clear sky", 0),
//    CLOUDS(801, "Clouds", "few clouds: 11-25%", 1);
//}
