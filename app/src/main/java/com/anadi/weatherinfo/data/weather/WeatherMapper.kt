package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.WeatherProvider
import com.anadi.weatherinfo.data.network.WeatherResponse

object WeatherMapper {

    fun update(oldEntity: Weather, response: WeatherResponse): Weather {
        return Weather(
                weatherId = oldEntity.weatherId,
                locationId = oldEntity.locationId,
                providerId = response.provider.code,
                code = response.code,
                temp = response.temp,
                tempFeelsLike = response.tempFeelsLike,
                windSpeed = response.windSpeed,
                windDegree = response.windDegree,
                pressure = response.pressure,
                humidity = response.humidity,
                clouds = response.clouds,
                dataCalcTimestamp = response.dataCalcTimestamp,
                downloadTimestamp = System.currentTimeMillis()
        )
    }

    fun convert(locationId: Int, response: WeatherResponse): Weather {
        return Weather(
                weatherId = 0,
                locationId = locationId,
                providerId = response.provider.code,
                code = response.code,
                temp = response.temp,
                tempFeelsLike = response.tempFeelsLike,
                windSpeed = response.windSpeed,
                windDegree = response.windDegree,
                pressure = response.pressure,
                humidity = response.humidity,
                clouds = response.clouds,
                dataCalcTimestamp = response.dataCalcTimestamp,
                downloadTimestamp = System.currentTimeMillis()
        )
    }

    fun merge(weathers: List<Weather>): Weather? {
        if (weathers.isEmpty()) {
            return null
        }

        val weatherId = weathers[0].weatherId
        val locationId = weathers[0].locationId
        val providerId = WeatherProvider.MERGED.code
        var code = 0
        var temp = 0
        var tempFeelsLike = 0
        var windSpeed = 0
        var windDegree = 0
        var pressure = 0
        var humidity = 0
        var clouds = 0
        val dataCalcTimestamp = weathers[0].dataCalcTimestamp
        val downloadTimestamp = weathers[0].downloadTimestamp

        for (weather in weathers) {
            code += weather.code
            temp += weather.temp
            tempFeelsLike += weather.tempFeelsLike
            windSpeed += weather.windSpeed
            windDegree += weather.windDegree
            pressure += weather.pressure
            humidity += weather.humidity
            clouds += weather.clouds
        }

        val count = weathers.size
        code /= count
        temp /= count
        tempFeelsLike /= count
        windSpeed /= count
        windDegree /= count
        pressure /= count
        humidity /= count
        clouds /= count

        return Weather(
                weatherId,
                locationId,
                providerId,
                code,
                temp,
                tempFeelsLike,
                windSpeed,
                windDegree,
                pressure,
                humidity,
                clouds,
                dataCalcTimestamp,
                downloadTimestamp
        )
    }
}