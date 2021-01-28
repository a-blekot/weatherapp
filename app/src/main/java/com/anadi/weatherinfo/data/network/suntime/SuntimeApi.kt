package com.anadi.weatherinfo.data.network.suntime

import retrofit2.http.GET
import retrofit2.http.Query

interface SuntimeApi {
    @GET("json")
    suspend fun getSuntime(
            @Query("lat")
            lat: Double,
            @Query("lng")
            lon: Double,
            @Query("formatted")
            formatted: Int, ): SuntimeResponse?
}