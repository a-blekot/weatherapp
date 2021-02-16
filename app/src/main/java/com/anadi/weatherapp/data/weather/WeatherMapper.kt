package com.anadi.weatherapp.data.weather

import com.anadi.weatherapp.data.db.BaseDao.Companion.NO_ID
import com.anadi.weatherapp.data.db.weather.Weather
import com.anadi.weatherapp.data.network.WeatherProvider
import com.anadi.weatherapp.data.network.WeatherResponse
import timber.log.Timber

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
                weatherId = NO_ID,
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

    // Deserialize firebase snapshot
    // Firebase stores Int as 64bit == Long
    fun update(oldEntity: Weather, rawData: Any): Weather {
        @Suppress("UNCHECKED_CAST")
        val map = rawData as Map<String, Any>

        return Weather(
                weatherId = oldEntity.weatherId,
                locationId = map["locationId"].toInt(),
                providerId = map["providerId"].toInt(),
                code = map["code"].toInt(),
                temp = map["temp"].toInt(),
                tempFeelsLike = map["tempFeelsLike"].toInt(),
                windSpeed = map["windSpeed"].toInt(),
                windDegree = map["windDegree"].toInt(),
                pressure = map["pressure"].toInt(),
                humidity = map["humidity"].toInt(),
                clouds = map["clouds"].toInt(),
                dataCalcTimestamp = map["dataCalcTimestamp"] as Long,
                downloadTimestamp = map["downloadTimestamp"] as Long
        )
    }

    // Deserialize firebase snapshot
    // Firebase stores Int as 64bit == Long
    fun convert(rawData: Any): Weather {
        @Suppress("UNCHECKED_CAST")
        val map = rawData as Map<String, Any>

        return Weather(
                weatherId = NO_ID,
                locationId = map["locationId"].toInt(),
                providerId = map["providerId"].toInt(),
                code = map["code"].toInt(),
                temp = map["temp"].toInt(),
                tempFeelsLike = map["tempFeelsLike"].toInt(),
                windSpeed = map["windSpeed"].toInt(),
                windDegree = map["windDegree"].toInt(),
                pressure = map["pressure"].toInt(),
                humidity = map["humidity"].toInt(),
                clouds = map["clouds"].toInt(),
                dataCalcTimestamp = map["dataCalcTimestamp"] as Long,
                downloadTimestamp = map["downloadTimestamp"] as Long
        )
    }

    private fun Any?.toInt() = (this as Long).toInt()

    fun merge(weathers: List<Weather>): Weather? {
        if (weathers.isEmpty()) {
            return null
        }

        val weatherId = weathers[0].weatherId
        val locationId = weathers[0].locationId
        val providerId = WeatherProvider.MERGED.code
        val codes = mutableListOf<Int>()
        var temp = 0
        var tempFeelsLike = 0
        var windSpeed = 0
        var windDegree = 0
        var pressure = 0
        var humidity = 0
        var clouds = 0
        var dataCalcTimestamp = 0L
        val downloadTimestamp = weathers[0].downloadTimestamp

        for (weather in weathers) {
            codes.add(weather.code)
            temp += weather.temp
            tempFeelsLike += weather.tempFeelsLike
            windSpeed += weather.windSpeed
            windDegree += weather.windDegree
            pressure += weather.pressure
            humidity += weather.humidity
            clouds += weather.clouds
            dataCalcTimestamp += weather.dataCalcTimestamp
        }

        val count = weathers.size
        val code = codes.random()
        temp /= count
        tempFeelsLike /= count
        windSpeed /= count
        windDegree /= count
        pressure /= count
        humidity /= count
        clouds /= count
        dataCalcTimestamp /= count

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
