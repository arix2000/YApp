package com.y.app.features.login.ui.state

sealed class LoginEvent {
    class LoginUser(val email: String, val password: String): LoginEvent()
    data object ClearErrorMessage : LoginEvent()
    data object LoginSavedUser: LoginEvent()
}