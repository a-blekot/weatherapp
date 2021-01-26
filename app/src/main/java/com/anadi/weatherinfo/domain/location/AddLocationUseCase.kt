package com.anadi.weatherinfo.domain.location

import android.content.Context
import android.widget.Toast
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.BaseDao.Companion.NO_ID
import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.UseCase
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import es.dmoral.toasty.Toasty
import javax.inject.Inject
import kotlin.random.Random

class AddLocationUseCase @Inject constructor(
        private val context: Context,
        private val locationRepository: LocationRepository,
        private val weatherRepositories: WeatherRepositories
) : UseCase<Unit, AddLocationUseCase.Params> {

    override suspend fun build(params: Params) {
        if (locationRepository.fetch(params.name) != null) {
            Toasty.info(context, R.string.location_already_added, Toasty.LENGTH_SHORT).show()
            return
        }

        val location = locationRepository.add(
                Location(NO_ID,
                         params.name,
                         params.address,
                         params.coord,
                         params.utcOffsetMinutes)
        )

        weatherRepositories.fetch(location)
    }

    class Params(val name: String,
                 val address: String,
                 val coord: Coord,
                 val utcOffsetMinutes: Int)
}
