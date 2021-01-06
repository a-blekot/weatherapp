package com.anadi.weatherinfo.data.db.weather

import androidx.room.Dao
import androidx.room.Query
import com.anadi.weatherinfo.data.db.BaseDao

@Dao
abstract class WeatherDao : BaseDao<Weather> {
    @Query("SELECT * FROM weather")
    abstract suspend fun fetchAll(): List<Weather>

    @Query("SELECT * FROM weather WHERE locationId = :locationId AND weatherId = :weatherId")
    abstract suspend fun fetch(locationId: Long, weatherId: Long): Weather?

    @Query("SELECT * FROM weather WHERE locationId = :id")
    abstract suspend fun fetchAllForLocation(id: Long): List<Weather>

    @Query("SELECT * FROM weather WHERE providerId = :id")
    abstract suspend fun fetchAllForProvider(id: Long): List<Weather>
}