package com.anadi.weatherapp.data.db.weather

import androidx.room.Dao
import androidx.room.Query
import com.anadi.weatherapp.data.db.BaseDao
import com.anadi.weatherapp.data.db.location.Location

@Dao
abstract class WeatherDao : BaseDao<Weather> {
    @Query("SELECT * FROM weather")
    abstract suspend fun fetchAll(): List<Weather>

    @Query("SELECT * FROM weather WHERE locationId = :locationId AND providerId = :providerId")
    abstract suspend fun fetch(locationId: Int, providerId: Int): Weather?

    @Query("SELECT * FROM weather ORDER BY weatherId DESC LIMIT 1")
    abstract suspend fun last(): Weather?

    @Query("SELECT * FROM weather WHERE locationId = :id")
    abstract suspend fun fetchAllForLocation(id: Int): List<Weather>

    @Query("SELECT * FROM weather WHERE providerId = :id")
    abstract suspend fun fetchAllForProvider(id: Int): List<Weather>
}
