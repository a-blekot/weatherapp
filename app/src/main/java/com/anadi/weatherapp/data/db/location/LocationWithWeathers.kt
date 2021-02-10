package com.anadi.weatherapp.data.db.location

import androidx.room.Embedded
import androidx.room.Relation
import com.anadi.weatherapp.data.db.weather.Weather

data class LocationWithWeathers(
        @Embedded
        val location: Location,

        @Relation(parentColumn = "id", entityColumn = "locationId", entity = Weather::class)
        val weathers: List<Weather>
)
