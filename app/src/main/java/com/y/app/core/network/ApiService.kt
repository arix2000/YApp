package com.y.app.core.network

import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login()
}