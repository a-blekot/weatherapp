package com.anadi.weatherinfo.domain.location

import com.anadi.weatherinfo.domain.UseCase
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import javax.inject.Inject

class CheckUpdatesAllLocationsUseCase @Inject constructor(
        private val locationRepository: LocationRepository, private val weatherRepositories: WeatherRepositories
) : UseCase<Unit, Unit?> {

    override suspend fun build(params: Unit?) {
        for (location in locationRepository.fetchAll()) {
            weatherRepositories.fetch(location)
        }
    }
}
