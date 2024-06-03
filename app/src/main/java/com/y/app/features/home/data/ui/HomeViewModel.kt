package com.y.app.features.home.data.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.models.PostFilterEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val postRepository: PostRepository) : ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state get() = _state.asStateFlow()

    init {
        invokeEvent(HomeEvent.GetPosts(PostFilterEnum.NEW))
    }

    fun invokeEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetPosts -> getPostsAndUser(event.filter)
        }
    }

    private fun getPostsAndUser(filter: PostFilterEnum) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            postRepository.getPosts(filter).collect(
                onSuccess = { posts ->
                    _state.update { it.copy(posts = posts, isLoading = false) }
                },
                onError = { message ->
                    _state.update {
                        it.copy(
                            errorMessage = message,
                            isLoading = false
                        )
                    }
                },
            )

            if (_state.value.user == null) {
                postRepository.getUser().collect { user ->
                    if (user != null)
                        _state.update { it.copy(user = user) }
                }
            }
        }
    }
}