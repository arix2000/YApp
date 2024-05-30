package com.y.app.features.registration.data

data class UserBody(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val avatarColor: String,
)