package com.y.app.features.home.ui

import com.y.app.features.home.data.models.Post
import com.y.app.features.login.data.models.User

data class HomeState(
    val posts: List<Post>? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)
