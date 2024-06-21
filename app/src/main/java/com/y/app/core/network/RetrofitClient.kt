package com.y.app.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    const val BASE_URL_HOST = "yapp-backend.azurewebsites.net"
    private const val BASE_URL = "https://${BASE_URL_HOST}/api/"

    fun create(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getLoggingInterceptorClient())
            .build()
            .create(ApiService::class.java)
    }

    private fun getLoggingInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}