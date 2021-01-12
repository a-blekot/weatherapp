package com.anadi.weatherinfo.data.network.openweather

import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.network.ResponseMapper
import com.anadi.weatherinfo.data.network.WeatherApi
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.network.WeatherResponse
import javax.inject.Inject

class OpenWeatherApiFacade @Inject constructor(private val openweatherApi: OpenWeatherApi) : WeatherApi {

    override val provider = WeatherProvider.OPEN_WEATHER

    override suspend fun getWeather(coord: Coord): WeatherResponse {
        val response = openweatherApi.getWeather(coord.lat, coord.lon, API_KEY, UNITS_METRIC, LANG_EN)
        return ResponseMapper.convert(response)
    }

    companion object {
        private const val API_KEY = "f9dee5683fdf51c7b611df7f57f26926"
        private const val UNITS_METRIC = "metric"
        private const val LANG_EN = "en"
    }
}
