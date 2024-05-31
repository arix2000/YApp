package com.y.app.features.login.data.models

import com.google.gson.Gson

data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val avatarColor: String,
) {

    fun toJson(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

   companion object {
       fun fromJson(json: String): User {
           val gson = Gson()
           return gson.fromJson(json, User::class.java)
       }
   }
}