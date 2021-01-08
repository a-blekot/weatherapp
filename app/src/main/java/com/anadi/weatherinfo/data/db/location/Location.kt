package com.anadi.weatherinfo.data.db.location

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @ColumnInfo(name = "city")
        var city: String = "",

        @Embedded(prefix = "country_")
        var country: Country = Country.EMPTY
)