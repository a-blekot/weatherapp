package com.anadi.weatherapp.data.db.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.anadi.weatherapp.data.db.BaseDao
import com.anadi.weatherapp.data.db.location.*

@Dao
abstract class UserDao : BaseDao<User> {
    @Query("SELECT * FROM User")
    abstract suspend fun fetchAll(): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    abstract suspend fun fetch(id: Int): User?

    @Query("SELECT * FROM user WHERE firebase_uid = :uid")
    abstract suspend fun fetch(uid: String): User?

    @Query("SELECT * FROM user ORDER BY id DESC LIMIT 1")
    abstract suspend fun last(): User?
}
