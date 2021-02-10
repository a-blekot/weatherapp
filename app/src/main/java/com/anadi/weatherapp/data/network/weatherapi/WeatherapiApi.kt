package com.anadi.weatherapp.data.network.weatherapi

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherapiApi {
    @GET("current.json")
    suspend fun getWeather(
            @Query("q")
            latLon: String, // comma separated
            @Query("key")
            appid: String,
            @Query("lang")
            lang: String
    ): WeatherapiResponse
}
