package com.anadi.weatherinfo.domain.location

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.domain.BaseRepository

interface LocationRepository: BaseRepository<Location> {
    suspend fun fetch(city: String, country: String): Location?

    suspend fun fetchAllWithWeathers(): List<LocationWithWeathers>

    suspend fun fetchWithWeathers(id: Int): LocationWithWeathers?
}