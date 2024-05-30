package com.y.app.features.registration.ui

import com.y.app.features.registration.data.UserBodyUi

sealed class RegistrationEvent {
    data object RegisterUser : RegistrationEvent()
    class UpdateUser(val userBodyUi: UserBodyUi): RegistrationEvent()
}