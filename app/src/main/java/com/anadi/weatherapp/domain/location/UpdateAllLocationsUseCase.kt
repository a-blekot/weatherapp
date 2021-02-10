package com.anadi.weatherapp.domain.location

import com.anadi.weatherapp.domain.UseCase
import com.anadi.weatherapp.domain.weather.WeatherRepositories
import javax.inject.Inject

class UpdateAllLocationsUseCase @Inject constructor(
        private val locationRepository: LocationRepository, private val weatherRepositories: WeatherRepositories
) : UseCase<Unit, Unit?> {

    override suspend fun build(params: Unit?) {
        for (location in locationRepository.fetchAll()) {
            weatherRepositories.update(location)
        }
    }
}
