package com.y.app.features.home.data.models

import com.y.app.features.login.data.models.User

data class Comment(
    val id: Int,
    val content: String,
    val user: User,
    val likesCount: Int,
    val isLikedByMe: Boolean,
    val date: String
)