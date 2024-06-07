package com.y.app.features.login.data.models

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val avatarColor: String,
) : Parcelable {

    val fullName get() = "$name $lastName"


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