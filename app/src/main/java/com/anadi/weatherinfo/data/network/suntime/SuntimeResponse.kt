package com.anadi.weatherinfo.data.network.suntime

import com.google.gson.annotations.SerializedName

data class SuntimeResponse(
        @SerializedName("results")
        val data: Data,
        @SerializedName("status")
        val status: String
)

data class Data(
        @SerializedName("sunrise")
        val sunrise: String,
        @SerializedName("sunset")
        val sunset: String,
        @SerializedName("solar_noon")
        val solarNoon: String,
        @SerializedName("day_length")
        val dayLength: String
)
