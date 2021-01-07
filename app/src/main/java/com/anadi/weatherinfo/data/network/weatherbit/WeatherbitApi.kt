package com.anadi.weatherinfo.data.network.weatherbit

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherbitApi {
    @GET("current/")
    suspend fun getWeather(@Query("city") location: String,
                           @Query("key") appid: String,
                           @Query("lang") lang: String): WeatherbitResponse

        //https://api.weatherbit.io/v2.0/current?city=London,Uk&key=f761fb9173b74f0e95cad844cdfa7d7c
//        http://dataservice.accuweather.com/currentconditions/v1/ 28143 /?apikey=ygxJ9SgrEenNrpsDWomQ9HMkOG3OH8Is&language=en-us&details=true
}