package com.anadi.weatherinfo.domain.weather

import com.anadi.weatherinfo.data.db.location.Location
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoriesImpl @Inject constructor(
        @Named("OpenWeather") weatherbit: WeatherRepository,
        @Named("Weatherbit") openweather: WeatherRepository
) : WeatherRepositories{
    private val repositories = listOf(openweather, weatherbit)

    override suspend fun fetch(city: String, country: String) {
        for (repository in repositories) {
            repository.fetch(city, country)
        }
    }

    override suspend fun update(location: Location) {
        for (repository in repositories) {
            repository.update(location)
        }
    }
}