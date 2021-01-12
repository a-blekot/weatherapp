package com.anadi.weatherinfo.data.weather

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoriesImpl @Inject constructor(
        @Named("OpenWeather") weatherbit: WeatherRepository,
        @Named("Weatherbit") openweather: WeatherRepository
) : WeatherRepositories {
    private val repositories = listOf(openweather, weatherbit)

    override suspend fun fetch(locationId: Int) {
        for (repository in repositories) {
            repository.fetch(locationId)
        }
    }

    override suspend fun update(location: Location) {
        for (repository in repositories) {
            repository.update(location)
        }
    }
}
