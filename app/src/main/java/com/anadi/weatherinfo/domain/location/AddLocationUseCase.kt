package com.anadi.weatherinfo.domain.location

import com.anadi.weatherinfo.data.db.BaseDao.Companion.NO_ID
import com.anadi.weatherinfo.data.db.location.Coord
import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.domain.UseCase
import com.anadi.weatherinfo.domain.weather.WeatherRepositories
import javax.inject.Inject
import kotlin.random.Random

class AddLocationUseCase @Inject constructor(
        private val locationRepository: LocationRepository, private val weatherRepositories: WeatherRepositories
) : UseCase<Unit, AddLocationUseCase.Params> {

    override suspend fun build(params: Params) {
        val location = locationRepository.add(
                Location(
                        NO_ID, params.name, params.address, params.coord, params.utcOffsetMinutes
                )
        )
        weatherRepositories.fetch(location)
    }

    class Params(
            val name: String, val address: String, val coord: Coord, val utcOffsetMinutes: Int
    ) {

        companion object {

            private const val MIN_LAT = -90
            private const val MAX_LAT = 90
            private const val MIN_LON = -180
            private const val MAX_LON = 180
            private const val MIN_UTC_OFFSET = -11
            private const val MAX_UTC_OFFSET = 13
            private const val MINUTES_IN_HOUR = 60

            private val rand = Random(System.currentTimeMillis())

            fun random() = Params(
                    name = "", address = "", coord = randomCoord(), utcOffsetMinutes = randomOffset()
            )

            private fun randomCoord() = Coord(
                    rand.nextInt(MIN_LAT, MAX_LAT).toDouble(), rand.nextInt(MIN_LON, MAX_LON).toDouble()
            )

            private fun randomOffset() = MINUTES_IN_HOUR * rand.nextInt(MIN_UTC_OFFSET, MAX_UTC_OFFSET)
        }
    }
}
