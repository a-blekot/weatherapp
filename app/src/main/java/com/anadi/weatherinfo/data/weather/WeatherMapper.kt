package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.WeatherResponse

object WeatherMapper {

    fun update(oldEntity: Weather, response: WeatherResponse): Weather {
        return Weather(
                weatherId = oldEntity.weatherId,
                locationId = oldEntity.locationId,
                providerId = response.provider.code,
                lon = response.lon,
                lat = response.lat,
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

    fun convert(locationId: Long, response: WeatherResponse): Weather {
        return Weather(
                weatherId = 0,
                locationId = locationId,
                providerId = response.provider.code,
                lon = response.lon,
                lat = response.lat,
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
}