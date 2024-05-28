package com.y.app.core.network

import android.util.Log

abstract class BaseRepository {

    suspend fun <T> makeHttpRequest(request: suspend () -> T): ApiResponse<T> {
        val result = try {
            request.invoke()
        } catch (e: Exception) {
            Log.e("SERVER_ERROR", e.stackTraceToString())
            return ApiResponse.Error(e.message ?: "UnexpectedError")
        }
        return ApiResponse.Success(result)
    }
}