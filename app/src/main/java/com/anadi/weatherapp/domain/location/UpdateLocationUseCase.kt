package com.anadi.weatherapp.domain.location

import com.anadi.weatherapp.domain.UseCase
import com.anadi.weatherapp.domain.weather.WeatherRepositories
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(
        private val locationRepository: LocationRepository, private val weatherRepositories: WeatherRepositories
) : UseCase<Unit, UpdateLocationUseCase.Params> {

    override suspend fun build(params: Params) {
        val location = locationRepository.fetch(params.locationId)
        location?.let { weatherRepositories.update(it) }
    }

    class Params(val locationId: Int)
}
