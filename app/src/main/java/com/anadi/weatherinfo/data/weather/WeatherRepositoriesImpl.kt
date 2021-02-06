package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoriesImpl @Inject constructor(
        @Named("OpenWeather")
        openweather: WeatherRepository,
        @Named("Weatherbit")
        weatherbit: WeatherRepository,
        @Named("Weatherapi")
        weatherapi: WeatherRepository
) : WeatherRepositories {
    private val repositories = listOf(openweather, weatherbit, weatherapi)

    override suspend fun fetch(location: Location) {
        for (repository in repositories) {
            repository.fetch(location)
        }
    }

    override suspend fun update(location: Location) = withContext(Dispatchers.IO) {
        coroutineScope {
            for (repository in repositories) {
                launch { repository.update(location) }
            }
        }
    }
}
