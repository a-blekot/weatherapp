package com.anadi.weatherinfo.data.db.location

import androidx.room.*
import com.anadi.weatherinfo.data.db.BaseDao

@Dao
abstract class LocationDao : BaseDao<Location> {
    @Query("SELECT * FROM location")
    abstract suspend fun fetchAll(): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    abstract suspend fun fetch(id: Int): Location?

    @Query("SELECT * FROM location WHERE city = :city AND country_name = :country")
    abstract suspend fun fetch(city: String, country: String): Location?

    @Transaction
    @Query("SELECT * FROM location")
    abstract suspend fun getLocationsWithWeathers(): List<LocationWithWeathers>

    @Transaction
    @Query("SELECT * FROM location WHERE id = :id")
    abstract suspend fun getLocationWithWeathers(id: Int): LocationWithWeathers?
}