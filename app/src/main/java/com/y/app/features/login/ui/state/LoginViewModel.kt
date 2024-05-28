package com.y.app.features.login.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.login.data.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private var _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state get() = _state.asStateFlow()

    private var loginJob: Job? = null

    fun login(email: String, password: String) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginRepository.loginUser(email, password)
                .collect(onSuccess = { user ->
                    _state.update { it.copy(user = user) }
                }, onError = { message ->
                    _state.update { it.copy(errorMessage = message) }
                })

        }
    }

}