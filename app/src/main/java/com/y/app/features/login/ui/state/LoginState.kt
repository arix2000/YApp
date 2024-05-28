package com.y.app.features.login.ui.state

import com.y.app.features.login.data.models.User

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)
