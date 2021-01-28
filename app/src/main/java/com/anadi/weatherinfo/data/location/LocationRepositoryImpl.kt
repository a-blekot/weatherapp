package com.anadi.weatherinfo.data.location

import com.anadi.weatherinfo.data.db.location.Location
import com.anadi.weatherinfo.data.db.location.LocationDao
import com.anadi.weatherinfo.data.db.location.LocationWithWeathers
import com.anadi.weatherinfo.data.network.state.NetworkMonitor
import com.anadi.weatherinfo.data.network.suntime.SuntimeApi
import com.anadi.weatherinfo.domain.location.LocationRepository
import com.anadi.weatherinfo.utils.DateFormats
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

private const val NOT_FORMATTED = 0

class LocationRepositoryImpl @Inject constructor(
        private val locationDao: LocationDao,
        private val suntimeApi: SuntimeApi,
        private val networkMonitor: NetworkMonitor
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

    override suspend fun add(obj: Location): Location {
        val location = updateSuntime(obj)
        locationDao.insert(location)
        return locationDao.last()!!
    }

    override suspend fun delete(obj: Location) {
        locationDao.delete(obj)
    }

    override suspend fun update(obj: Location) {
        locationDao.update(obj)
    }

    override suspend fun updateSuntime() {
        for (location in locationDao.fetchAll()) {
            locationDao.update(updateSuntime(location))
        }
    }

    private suspend fun updateSuntime(location: Location): Location {
        if (networkMonitor.offline) {
            return location
        }

        if(location.sunrise.isToday) {
            return location
        }

        val response = suntimeApi.getSuntime(location.coord.lat, location.coord.lon, NOT_FORMATTED)

        return response?.data?.let {
            location.copy(
                    sunrise = DateFormats.sunDatetime.parseDateTime(it.sunrise),
                    sunset = DateFormats.sunDatetime.parseDateTime(it.sunset)
            )
        } ?: location
    }

    private val DateTime.isToday: Boolean
        get() = this.toLocalDate() == LocalDate()
}
