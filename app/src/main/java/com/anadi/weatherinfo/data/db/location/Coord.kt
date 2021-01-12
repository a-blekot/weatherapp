package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo
import com.google.android.gms.maps.model.LatLng
import kotlin.math.max
import kotlin.math.min

data class Coord(
        @ColumnInfo(name = "lat")
        val lat: Double,

        @ColumnInfo(name = "lon")
        val lon: Double) {

    companion object {
        operator fun invoke(lat: Double, lon: Double): Coord {

            val checkedLon: Double
            @Suppress("LiftReturnOrAssignment")
            if (lon.compareTo(-180.0) > 0 && lon.compareTo(180.0) < 0) {
                checkedLon = lon
            } else {
                checkedLon = ((lon - 180.0) % 360.0 + 360.0) % 360.0 - 180.0
            }

            val checkedLat: Double = max(-90.0, min(90.0, lat))

            return Coord(checkedLat, checkedLon)
        }

        fun from(coord: LatLng) = Coord(coord.latitude, coord.longitude)
    }
}