package com.anadi.weatherinfo.repository.data

import com.google.gson.annotations.SerializedName

data class Main(var temp: Float = 0f,
                @SerializedName("feels_like") var feelsLike: Float = 0f,
                @SerializedName("temp_min") var tempMin: Float = 0f,
                @SerializedName("temp_max") var tempMax: Float = 0f,
                var pressure: Int = 0,
                var humidity: Int = 0)