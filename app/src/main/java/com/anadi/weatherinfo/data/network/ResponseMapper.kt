package com.anadi.weatherinfo.data.network

import com.anadi.weatherinfo.data.network.openweather.OpenWeatherResponse
import com.anadi.weatherinfo.data.network.weatherbit.WeatherbitResponse

object ResponseMapper {
    fun convert (response: OpenWeatherResponse): WeatherResponse {
        require(response.weather.isNotEmpty()) {
            "OpenWeatherResponse -> weather: List<WeatherMain> is empty"
        }

        return WeatherResponse(
                provider = WeatherProvider.OPEN_WEATHER,
                lon = response.coord.lon,
                lat = response.coord.lat,
                code = response.weather[0].code,
                temp = response.main.temp.toInt(),
                tempFeelsLike = response.main.tempFeelsLike.toInt(),
                windSpeed = response.wind.speed.toInt(),
                windDegree = response.wind.deg,
                pressure = response.main.pressure,
                humidity = response.main.humidity,
                clouds = response.clouds.all,
                dataCalcTimestamp = response.dataCalcTimestamp
        )
    }

    fun convert (response: WeatherbitResponse): WeatherResponse {
        require(response.data.isNotEmpty()) {
            "WeatherbitResponse -> data: List<WeatherbitData> is empty"
        }

        val weatherbit = response.data.last()

        return WeatherResponse(
                provider = WeatherProvider.WEATHER_BIT,
                lon = weatherbit.lon,
                lat = weatherbit.lat,
                code = weatherbit.weather.code,
                temp = weatherbit.temp.toInt(),
                tempFeelsLike = weatherbit.tempFeelsLike.toInt(),
                windSpeed = weatherbit.windSpeed.toInt(),
                windDegree = 360 - weatherbit.windDegree,
                pressure = weatherbit.pressure.toInt(),
                humidity = weatherbit.humidity.toInt(),
                clouds = weatherbit.clouds,
                dataCalcTimestamp = weatherbit.dataCalcTimestamp,
        )
    }
}