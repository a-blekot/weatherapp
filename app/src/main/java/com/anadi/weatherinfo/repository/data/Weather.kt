package com.anadi.weatherinfo.repository.data

data class Weather(var id: Int = 0,
                   var main: String, // Group of weather parameters (Rain, Snow, Extreme etc.)
                   var description: String, // Weather condition within the group. You can get the output in your language. Learn more
                   var icon: String)
