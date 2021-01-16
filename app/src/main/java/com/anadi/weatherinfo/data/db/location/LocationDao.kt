package com.anadi.weatherinfo.data.db.location

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.anadi.weatherinfo.data.db.BaseDao

@Dao
abstract class LocationDao : BaseDao<Location> {
    @Query("SELECT * FROM location")
    abstract suspend fun fetchAll(): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    abstract suspend fun fetch(id: Int): Location?

    @Query("SELECT * FROM location WHERE name = :name")
    abstract suspend fun fetch(name: String): Location?

    @Query("SELECT * FROM location ORDER BY id DESC LIMIT 1")
    abstract suspend fun last(): Location?

    @Transaction
    @Query("SELECT * FROM location")
    abstract suspend fun getLocationsWithWeathers(): List<LocationWithWeathers>

    @Transaction
    @Query("SELECT * FROM location WHERE id = :id")
    abstract suspend fun getLocationWithWeathers(id: Int): LocationWithWeathers?
}
