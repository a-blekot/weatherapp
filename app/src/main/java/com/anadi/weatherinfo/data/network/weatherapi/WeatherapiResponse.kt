package com.anadi.weatherinfo.data.network.weatherapi

import com.google.gson.annotations.SerializedName

data class WeatherapiResponse(
    val current: Current
)

data class Current(
        @SerializedName("condition")
        val condition: Condition,

        @SerializedName("temp_c")
        val temp: Float,
        @SerializedName("feelslike_c")
        val tempFeelsLike: Float,

        @SerializedName("wind_kph")
        val windSpeed: Float,
        @SerializedName("wind_degree")
        val windDegree: Int,

        @SerializedName("pressure_mb")
        val pressure: Float,
        @SerializedName("humidity")
        val humidity: Int,

        @SerializedName("cloud")
        val clouds: Int,

        @SerializedName("uv")
        val uv: Float,

        @SerializedName("last_updated_epoch")
        val dataCalcTimestamp: Int, // Unix timestamp in seconds
)

data class Condition(val code: Int)
