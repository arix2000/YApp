package com.y.app.features.registration.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.login.data.UserRepository
import com.y.app.features.login.data.models.RegistrationResult
import com.y.app.features.registration.data.UserBodyUi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _state: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState())
    val state get() = _state.asStateFlow()

    private val _userUiState: MutableStateFlow<UserBodyUi> = MutableStateFlow(UserBodyUi.EMPTY)
    val userBodyUiState get() = _userUiState.asStateFlow()

    fun invokeEvent(event: RegistrationEvent) {
        when (event) {
            RegistrationEvent.RegisterUser -> registerUser()
            is RegistrationEvent.UpdateUser -> updateUserBody(event.userBodyUi)
        }
    }

    private fun updateUserBody(userBodyUi: UserBodyUi) {
        _userUiState.update { userBodyUi }
    }

    private var registerJob: Job? = null

    private fun registerUser() {
        registerJob?.cancel()
        registerJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userRepository.registerUser(userBodyUiState.value.toUserBody())
                .collect(onSuccess = { response ->
                    _state.update {
                        it.copy(
                            registrationResult = response.result, isLoading = false
                        )
                    }
                }, onError = { message ->
                    if (message.contains("409")) {
                        _userUiState.value.emailTaken = true
                        _state.update {
                            it.copy(
                                registrationResult = RegistrationResult.EMAIL_TAKEN,
                                isLoading = false
                            )
                        }
                    } else {
                        _state.update { it.copy(errorMessage = message, isLoading = false) }
                    }
                })
        }
    }
}