package com.anadi.weatherapp.data.db.location

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anadi.weatherapp.data.db.Converters
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Entity(tableName = "location")
data class Location(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "google_place_id")
        val googlePlaceId: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "address")
        val address: String,

        @Embedded(prefix = "coord_")
        val coord: Coord,

        @ColumnInfo(name = "time_zone")
        val timeZone: DateTimeZone,

        @ColumnInfo(name = "sunrise")
        var sunrise: DateTime = DateTime(0L),

        @ColumnInfo(name = "sunset")
        var sunset: DateTime = DateTime(0L)
) {
    init {
        sunrise = sunrise.toDateTime(timeZone)
        sunset = sunset.toDateTime(timeZone)
    }

    fun isDayNow(): Boolean {
        return sunrise.isBeforeNow && sunset.isAfterNow
    }

    fun toMap() = mapOf(
            "name" to name,
            "address" to address,
            "coord" to coord,
            "timeZone" to Converters.fromDateTimeZone(timeZone),
            "sunrise" to Converters.fromDateTime(sunrise),
            "sunset" to Converters.fromDateTime(sunset),
            "weather" to emptyList<String>(),
            "messages" to emptyList<String>()
    )
}
