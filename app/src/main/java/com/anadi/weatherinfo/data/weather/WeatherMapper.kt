package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.weather.Weather
import com.anadi.weatherinfo.data.network.*

object WeatherMapper {
    fun convert(data: Weather): WeatherResponse {
        return WeatherResponse(
                coord = Coord(data.lon / 1000F, data.lat / 1000F),
                weather = listOf(WeatherMain(0, "", data.icon ?: "")),
                base = "",
                main = Main(data.temp.toFloat(), data.pressure, data.humidity),
                wind = Wind(data.windSpeed.toFloat(), data.windDegree),
                clouds = Clouds(data.cloudiness),
                sys = Sys(""),
                timezone = 0,
                id = 0,
                name = "",
                cod = 0
        )
    }

    fun update(oldEntity: Weather, data: WeatherResponse): Weather {
        return Weather(
                weatherId = oldEntity.weatherId,
                locationId = oldEntity.locationId,
                providerId = oldEntity.providerId,
                lon = (data.coord.lon * 1000).toLong(),
                lat = (data.coord.lat * 1000).toLong(),
                icon = data.weather[0].icon,
                temp = data.main.temp.toInt(),
                pressure = data.main.pressure,
                humidity = data.main.humidity,
                windSpeed = data.wind.speed.toInt(),
                windDegree = data.wind.deg,
                cloudiness = data.clouds.all,
                lastUpdate = System.currentTimeMillis()
        )
    }

    fun convert(data: WeatherResponse): Weather {
        return Weather(
                weatherId = 0,
                locationId = 1,
                providerId = 1,
                lon = (data.coord.lon * 1000).toLong(),
                lat = (data.coord.lat * 1000).toLong(),
                icon = data.weather[0].icon,
                temp = data.main.temp.toInt(),
                pressure = data.main.pressure,
                humidity = data.main.humidity,
                windSpeed = data.wind.speed.toInt(),
                windDegree = data.wind.deg,
                cloudiness = data.clouds.all,
                lastUpdate = System.currentTimeMillis()
        )
    }
}