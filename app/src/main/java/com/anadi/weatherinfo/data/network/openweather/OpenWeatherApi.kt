package com.anadi.weatherinfo.data.network.openweather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather/")
    suspend fun getWeather(@Query("lat") lat: Double,
                           @Query("lon") lon: Double,
                           @Query("appid") appid: String,
                           @Query("units") units: String,
                           @Query("lang") lang: String): OpenWeatherResponse
}