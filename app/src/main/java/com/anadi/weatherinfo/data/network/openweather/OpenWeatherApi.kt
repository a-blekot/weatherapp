package com.anadi.weatherinfo.data.network.openweather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather/")
    suspend fun getWeather(@Query("q") location: String,
                           @Query("appid") appid: String,
                           @Query("units") units: String,
                           @Query("lang") lang: String): OpenWeatherResponse

    companion object {
        private const val API_KEY = "f9dee5683fdf51c7b611df7f57f26926"
    }
}