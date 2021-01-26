package com.anadi.weatherinfo.data.network.weatherapi

import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.network.ResponseMapper
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.network.WeatherResponse
import javax.inject.Inject

class WeatherapiApiFacade @Inject constructor(private val weatherapiApi: WeatherapiApi) : WeatherApi {

    override val provider = WeatherProvider.WEATHER_API

    override suspend fun getWeather(coord: Coord): WeatherResponse {
        val query = "${coord.lat},${coord.lon}"
        val response = weatherapiApi.getWeather(query, API_KEY, LANG_EN)
        return ResponseMapper.convert(response)
    }

    companion object {
        private const val API_KEY = "f7ffe09aa3754a2cbec110503211701"
        private const val LANG_EN = "en"
    }
}
