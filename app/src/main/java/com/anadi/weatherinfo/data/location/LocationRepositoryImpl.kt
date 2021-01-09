package com.anadi.weatherinfo.data.location

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationDao
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.domain.location.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
        private val locationDao: LocationDao
) : LocationRepository {
    override suspend fun fetchAll(): List<Location> {
        return locationDao.fetchAll()
    }

    override suspend fun fetch(id: Int): Location? {
        return locationDao.fetch(id)
    }

    override suspend fun fetch(name: String): Location? {
        return locationDao.fetch(name)
    }

    override suspend fun fetchAllWithWeathers(): List<LocationWithWeathers> {
        return locationDao.getLocationsWithWeathers()
    }

    override suspend fun fetchWithWeathers(id: Int): LocationWithWeathers? {
        return locationDao.getLocationWithWeathers(id)
    }

    override suspend fun add(obj: Location): Int {
        return locationDao.insert(obj).toInt()
    }

    override suspend fun delete(obj: Location) {
        locationDao.delete(obj)
    }
    
    override suspend fun update(obj: Location) {
        locationDao.update(obj)
    }
}
