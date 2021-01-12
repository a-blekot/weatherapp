package com.anadi.weatherinfo.data.network.weatherbit

import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.network.ResponseMapper
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.network.WeatherResponse
import javax.inject.Inject

class WeatherbitApiFacade @Inject constructor(private val weatherbitApi: WeatherbitApi) : WeatherApi {

    override val provider = WeatherProvider.WEATHER_BIT

    override suspend fun getWeather(coord: Coord): WeatherResponse {
        val response = weatherbitApi.getWeather(coord.lat, coord.lon, API_KEY, LANG_EN)
        return ResponseMapper.convert(response)
    }

    companion object {
        private const val API_KEY = "f761fb9173b74f0e95cad844cdfa7d7c"
        private const val LANG_EN = "en"
    }
}
