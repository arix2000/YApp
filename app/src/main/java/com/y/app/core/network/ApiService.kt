package com.y.app.core.network

import com.y.app.features.login.data.models.Credentials
import com.y.app.features.login.data.models.RegistrationResponse
import com.y.app.features.login.data.models.User
import com.y.app.features.registration.data.UserBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun login(@Body credentials: Credentials): User

    @POST("register")
    suspend fun register(@Body user: UserBody): RegistrationResponse
}