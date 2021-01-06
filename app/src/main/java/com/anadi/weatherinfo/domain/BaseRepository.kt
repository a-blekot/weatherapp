package com.anadi.weatherinfo.domain

interface BaseRepository<T> {
    suspend fun fetchAll(): List<T>

    suspend fun fetch(id: Long): T?

    suspend fun add(obj: T)

    suspend fun delete(obj: T)

    suspend fun update(obj: T): T
}