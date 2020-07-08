package com.anadi.weatherinfo.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapInterface {
//    @GET("weather/")
//    void getWeather(@Query("q") String location,
//                    @Query("appid") String appid,
//                    @Query("units") String units,
//                    Callback<WeatherInfoSuper> cb);

    @GET("weather/")
    Call<WeatherInfo> getWeather(@Query("q") String location,
                                 @Query("appid") String appid,
                                 @Query("units") String units,
                                 @Query("lang") String lang);
}
