package com.y.app.features.registration.ui

import com.y.app.features.login.data.models.RegistrationResult

data class RegistrationState(
    val isLoading: Boolean = false,
    val registrationResult: RegistrationResult? = null,
    val errorMessage: String? = null,
)