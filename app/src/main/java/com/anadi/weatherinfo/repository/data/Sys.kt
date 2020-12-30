package com.anadi.weatherinfo.repository.data

data class Sys(var type: Int = 0,
               var id: Int = 0,
               var country: String, // Country code (GB, JP etc.)
               var sunrise: Int = 0,// time, unix, UTC
               var sunset: Int = 0)
