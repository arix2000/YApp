package com.y.app.core.network

sealed class ApiResponse<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Error<T>(message: String, data: T? = null) : ApiResponse<T>(data, message)

    fun collect(onSuccess: (data: T) -> Unit, onError: (message: String) -> Unit) {
        when(this) {
            is Error -> onError(message!!)
            is Success -> onSuccess(data!!)
        }
    }

    fun <R> mapSuccess(converter: (T) -> R): ApiResponse<R> {
        return if (this is Success)
            Success(converter(data!!))
        else Error(message!!, null)
    }

    fun isSuccess(): Boolean {
        return this is Success
    }
}