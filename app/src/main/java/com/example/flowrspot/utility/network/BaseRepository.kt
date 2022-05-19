package com.example.flowrspot.utility.network

open class BaseRepository {
    inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unknown error!")
        }
    }
}