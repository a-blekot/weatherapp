package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo
import com.google.android.gms.maps.model.LatLng
import kotlin.math.max
import kotlin.math.min

data class Coord(
        @ColumnInfo(name = "lat")
        val lat: Double,

        @ColumnInfo(name = "lon")
        val lon: Double
) {

    companion object {
        operator fun invoke(lat: Double, lon: Double): Coord {

            val checkedLon: Double
            @Suppress("LiftReturnOrAssignment") if (lon in -PI_RADIAN..PI_RADIAN) {
                checkedLon = lon
            } else {
                checkedLon = ((lon - PI_RADIAN) % PI2_RADIAN + PI2_RADIAN) % PI2_RADIAN - PI_RADIAN
            }

            val checkedLat: Double = max(MIN_LAT, min(MAX_LAT, lat))

            return Coord(checkedLat, checkedLon)
        }

        fun from(coord: LatLng) = Coord(coord.latitude, coord.longitude)

        private const val PI2_RADIAN = 360.0
        const val PI_RADIAN = 180.0
        private const val MIN_LAT = -90.0
        private const val MAX_LAT = -90.0
    }
}
