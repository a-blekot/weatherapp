package com.anadi.weatherinfo.data.network.weatherbit

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitApi {
    @GET("current/")
    suspend fun getWeather(@Query("lat") lat: Double,
                           @Query("lon") lon: Double,
                           @Query("key") key: String,
                           @Query("lang") lang: String): WeatherbitResponse

//        http://dataservice.accuweather.com/currentconditions/v1/ 28143 /?apikey=ygxJ9SgrEenNrpsDWomQ9HMkOG3OH8Is&language=en-us&details=true
}