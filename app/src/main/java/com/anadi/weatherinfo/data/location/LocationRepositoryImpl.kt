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

    override suspend fun fetch(id: Long): Location? {
        return locationDao.fetch(id)
    }

    override suspend fun fetch(city: String, country: String): Location? {
        return locationDao.fetch(city, country)
    }

    override suspend fun fetchAllWithWeathers(): List<LocationWithWeathers> {
        return locationDao.getLocationsWithWeathers()
    }

    override suspend fun fetchWithWeathers(id: Long): LocationWithWeathers? {
        return locationDao.getLocationWithWeathers(id)
    }

    override suspend fun add(obj: Location) {
        locationDao.insert(obj)
    }

    override suspend fun delete(obj: Location) {
        locationDao.delete(obj)
    }
    
    override suspend fun update(obj: Location): Location {
        locationDao.insert(obj)
        return obj
    }
}
