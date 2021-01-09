package com.anadi.weatherinfo.domain

interface BaseRepository<T> {
    suspend fun fetchAll(): List<T>

    suspend fun fetch(id: Int): T?

    suspend fun add(obj: T): Int

    suspend fun delete(obj: T)

    suspend fun update(obj: T)
}