package com.anadi.weatherinfo.data.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapInterface {
    @GET("weather/")
    fun getWeather(@Query("q") location: String, @Query("appid") appid: String,
                   @Query("units") units: String, @Query("lang") lang: String): Call<WeatherInfo>
}