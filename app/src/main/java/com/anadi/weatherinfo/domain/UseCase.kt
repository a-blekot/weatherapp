package com.anadi.weatherinfo.domain

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 */
interface UseCase<out T, in Params> {
    suspend fun build(params: Params): T
}
