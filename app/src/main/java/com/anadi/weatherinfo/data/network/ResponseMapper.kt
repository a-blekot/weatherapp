package com.anadi.weatherinfo.data.network

import com.anadi.weatherinfo.data.network.openweather.OpenWeatherResponse
import com.anadi.weatherinfo.data.network.weatherapi.WeatherapiResponse
import com.anadi.weatherinfo.data.network.weatherbit.WeatherbitResponse

object ResponseMapper {
    fun convert(response: OpenWeatherResponse): WeatherResponse {
        require(response.weather.isNotEmpty()) {
            "OpenWeatherResponse -> weather: List<WeatherMain> is empty"
        }

        return WeatherResponse(
                provider = WeatherProvider.OPEN_WEATHER,
                code = response.weather[0].code,
                temp = response.main.temp.toInt(),
                tempFeelsLike = response.main.tempFeelsLike.toInt(),
                windSpeed = response.wind.speed.toInt(),
                windDegree = response.wind.deg,
                pressure = response.main.pressure,
                humidity = response.main.humidity,
                clouds = response.clouds.all,
                dataCalcTimestamp = response.dataCalcTimestamp * MILLISECONDS
        )
    }

    fun convert(response: WeatherbitResponse): WeatherResponse {
        require(response.data.isNotEmpty()) {
            "WeatherbitResponse -> data: List<WeatherbitData> is empty"
        }

        val weatherbit = response.data.last()

        return WeatherResponse(
                provider = WeatherProvider.WEATHER_BIT,
                code = weatherbit.weather.code,
                temp = weatherbit.temp.toInt(),
                tempFeelsLike = weatherbit.tempFeelsLike.toInt(),
                windSpeed = weatherbit.windSpeed.toInt(),
                windDegree = PI2_RADIAN - weatherbit.windDegree,
                pressure = weatherbit.pressure.toInt(),
                humidity = weatherbit.humidity.toInt(),
                clouds = weatherbit.clouds,
                dataCalcTimestamp = weatherbit.dataCalcTimestamp * MILLISECONDS,
        )
    }

    fun convert(response: WeatherapiResponse): WeatherResponse {
        val weatherapi = response.current

        return WeatherResponse(
                provider = WeatherProvider.WEATHER_API,
                code = weatherapi.condition.code,
                temp = weatherapi.temp.toInt(),
                tempFeelsLike = weatherapi.tempFeelsLike.toInt(),
                windSpeed = weatherapi.windSpeed.toInt(),
                windDegree = weatherapi.windDegree, // PI2_RADIAN -
                pressure = weatherapi.pressure.toInt(),
                humidity = weatherapi.humidity,
                clouds = weatherapi.clouds,
                dataCalcTimestamp = weatherapi.dataCalcTimestamp * MILLISECONDS,
        )
    }

    private const val PI2_RADIAN = 360
    private const val MILLISECONDS = 1000L
}
