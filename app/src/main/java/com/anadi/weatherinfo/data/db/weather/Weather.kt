package com.anadi.weatherinfo.data.db.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather(

        @PrimaryKey(autoGenerate = true)
        var weatherId: Long,

        @ColumnInfo(name = "locationId")
        val locationId: Long,

        @ColumnInfo(name = "providerId")
        val providerId: Int,

        @ColumnInfo(name = "lon")
        val lon: Long,

        @ColumnInfo(name = "lat")
        val lat: Long,

        @ColumnInfo(name = "icon")
        val icon: String?,

        @ColumnInfo(name = "temp")
        val temp: Int,

        @ColumnInfo(name = "pressure")
        val pressure: Int,

        @ColumnInfo(name = "humidity")
        val humidity: Int,

        @ColumnInfo(name = "wind_speed")
        val windSpeed: Int,

        @ColumnInfo(name = "wind_degree")
        val windDegree: Int,

        @ColumnInfo(name = "cloudiness")
        val cloudiness: Int,

        @ColumnInfo(name = "lastUpdate")
        val lastUpdate: Long
)