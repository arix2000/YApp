package com.y.app.features.login.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.login.data.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private var _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state get() = _state.asStateFlow()

    private var loginJob: Job? = null

    fun invokeEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.LoginUser -> login(event.email, event.password)
            LoginEvent.ClearErrorMessage -> clearErrorMessage()
            LoginEvent.LoginSavedUser -> loginSavedUser()
        }
    }

    private fun clearErrorMessage() {
        _state.update {
            it.copy(errorMessage = null)
        }
    }

    private fun login(email: String, password: String) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userRepository.loginUser(email, password)
                .collect(onSuccess = { user ->
                    _state.update { it.copy(user = user, isLoading = false) }
                }, onError = { message ->
                    _state.update { it.copy(errorMessage = message, isLoading = false) }
                })

        }
    }

    private fun loginSavedUser() {
        viewModelScope.launch {
            userRepository.getUser().collect { user ->
                if (user != null) {
                    _state.update { it.copy(user = user) }
                }
            }
        }
    }

}