package com.anadi.weatherinfo.data.db.location

import androidx.room.Embedded
import androidx.room.Relation
import com.anadi.weatherinfo.data.db.weather.Weather

data class LocationWithWeathers(
        @Embedded val location: Location,

        @Relation(parentColumn = "id", entityColumn = "locationId", entity = Weather::class)
        val weathers: List<Weather>
)