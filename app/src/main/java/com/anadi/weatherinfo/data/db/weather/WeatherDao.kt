package com.anadi.weatherinfo.data.db.weather

import androidx.room.Dao
import androidx.room.Query
import com.anadi.weatherinfo.data.db.BaseDao

@Dao
abstract class WeatherDao : BaseDao<Weather> {
    @Query("SELECT * FROM weather")
    abstract suspend fun fetchAll(): List<Weather>

    @Query("SELECT * FROM weather WHERE locationId = :locationId AND providerId = :providerId")
    abstract suspend fun fetch(locationId: Int, providerId: Int): Weather?

    @Query("SELECT * FROM weather WHERE locationId = :id")
    abstract suspend fun fetchAllForLocation(id: Int): List<Weather>

    @Query("SELECT * FROM weather WHERE providerId = :id")
    abstract suspend fun fetchAllForProvider(id: Int): List<Weather>
}