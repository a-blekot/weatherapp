package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

@Entity(tableName = "location")
data class Location(

        @PrimaryKey(autoGenerate = true)
        val id: Int,

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
}
