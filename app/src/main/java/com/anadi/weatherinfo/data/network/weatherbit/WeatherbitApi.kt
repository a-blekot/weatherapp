package com.anadi.weatherinfo.data.network.weatherbit

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitApi {
    @GET("current/")
    suspend fun getWeather(@Query("lat") lat: Double,
                           @Query("lon") lon: Double,
                           @Query("key") key: String,
                           @Query("lang") lang: String): WeatherbitResponse
}
