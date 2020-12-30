package com.anadi.weatherinfo.repository.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapInterface {
    //    @GET("weather/")
    //    void getWeather(@Query("q") String location,
    //                    @Query("appid") String appid,
    //                    @Query("units") String units,
    //                    Callback<WeatherInfoSuper> cb);
    @GET("weather/")
    fun getWeather(@Query("q") location: String?, @Query("appid") appid: String?,
                   @Query("units") units: String?, @Query("lang") lang: String?): Call<WeatherInfo?>?
}