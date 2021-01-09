package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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

        @ColumnInfo(name = "utc_offset_minutes")
        val utcOffsetMinutes: Int,

        @ColumnInfo(name = "sunrise")
        val sunrise: Long = 0,

        @ColumnInfo(name = "sunset")
        val sunset: Long = 0
)