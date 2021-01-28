package com.anadi.weatherinfo.domain.location

import android.content.Context
import com.anadi.weatherinfo.R
import com.anadi.weatherinfo.data.db.BaseDao.Companion.NO_ID
import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.UseCase
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import es.dmoral.toasty.Toasty
import org.joda.time.DateTimeZone
import javax.inject.Inject

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
                         DateTimeZone.forOffsetMillis(params.utcOffsetMillis)
                )
        )

        weatherRepositories.fetch(location)
    }

    class Params(val name: String,
                 val address: String,
                 val coord: Coord,
                 val utcOffsetMillis: Int)
}
