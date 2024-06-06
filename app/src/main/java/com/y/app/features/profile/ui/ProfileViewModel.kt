package com.y.app.features.profile.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.core.local.DataStoreManager
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.models.PostFilterEnum
import com.y.app.features.home.data.models.bodies.PostLikeBody
import com.y.app.features.login.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state get() = _state.asStateFlow()

    fun invokeEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.LikePost -> likePost(event)
            is ProfileEvent.GetInitialData -> getInitialData(event.userId)
            ProfileEvent.LogoutUser -> logoutUser()
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            dataStore.clearUser()
            _state.update { it.copy(isUserDeleted = true) }
        }
    }

    private fun getInitialData(userId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            userRepository.getUserPosts(userId, PostFilterEnum.NEW).collect(onSuccess = { posts ->
                userRepository.getUser().collect { currentUser ->
                    userRepository.getUser(userId).collect(onSuccess = { user ->
                        _state.update {
                            it.copy(
                                user = user,
                                posts = posts.toMutableStateList(),
                                isLoading = false,
                                isOwnProfile = currentUser?.id == user.id
                            )
                        }
                    }, onError = { message ->
                        _state.update {
                            it.copy(errorMessage = message, isLoading = false)
                        }
                    })
                }
            }, onError = { message ->
                _state.update {
                    it.copy(
                        errorMessage = message, isLoading = false
                    )
                }
            })
        }
    }

    private fun likePost(event: ProfileEvent.LikePost) {
        viewModelScope.launch {
            postRepository.likePost(PostLikeBody(event.userId, event.postId))
                .collect(onSuccess = { /** Do nothing **/ },
                    onError = { message -> _state.update { it.copy(errorMessage = message) } })
        }
    }


}