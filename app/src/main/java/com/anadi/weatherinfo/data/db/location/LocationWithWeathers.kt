package com.anadi.weatherinfo.data.db.location

import androidx.room.Embedded
import androidx.room.Relation
import com.anadi.weatherinfo.data.db.weather.Weather

data class LocationWithWeathers(
        @Embedded val location: Location,

        @Relation(parentColumn = "locationId", entityColumn = "weatherId")
        val weathers: List<Weather>
)