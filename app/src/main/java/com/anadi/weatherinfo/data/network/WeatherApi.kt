package com.anadi.weatherinfo.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather/")
    suspend fun getWeather(@Query("q") location: String,
                           @Query("appid") appid: String,
                           @Query("units") units: String,
                           @Query("lang") lang: String): WeatherResponse

    companion object {
        private const val API_KEY = "f9dee5683fdf51c7b611df7f57f26926"
    }
}