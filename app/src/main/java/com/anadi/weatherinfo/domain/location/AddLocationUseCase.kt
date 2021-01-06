package com.anadi.weatherinfo.domain.location

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.UseCase
import com.anadi.weatherinfo.domain.weather.WeatherRepository
import com.anadi.weatherinfo.view.ui.addlocation.LocationsProvider
import javax.inject.Inject

class AddLocationUseCase @Inject constructor(
        private val locationRepository: LocationRepository,
        private val weatherRepository: WeatherRepository,
        private val locationsProvider: LocationsProvider
): UseCase<Unit, AddLocationUseCase.Params>() {

    override suspend fun build(params: Params) {
        val location = Location().apply {
            city = params.city
            country = locationsProvider.getCountryByName(params.country)
        }

        locationRepository.add(location)
        weatherRepository.fetch(params.city, params.country)
    }

    class Params(val city: String, val country: String)
}