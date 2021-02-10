package com.anadi.weatherapp.domain.location

import com.anadi.weatherapp.data.db.location.Location
import com.anadi.weatherapp.data.db.location.LocationWithWeathers
import com.anadi.weatherapp.domain.BaseRepository

interface LocationRepository : BaseRepository<Location> {
    suspend fun fetch(name: String): Location?

    suspend fun fetchAllWithWeathers(): List<LocationWithWeathers>

    suspend fun fetchWithWeathers(id: Int): LocationWithWeathers?

    suspend fun updateSuntime()
}
