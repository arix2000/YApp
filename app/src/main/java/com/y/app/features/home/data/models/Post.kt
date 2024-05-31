package com.y.app.features.home.data.models

import com.y.app.features.login.data.models.User

data class Post(
    val id: Int,
    val author: User,
    val content: String,
    val likesCount: Int,
    val commentsCount: Int,
    val isLikedByMe: Boolean,
    val imageUrl: String?,
    val date: String
)