package com.y.app.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.common.extensions.sortByDate
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.models.PostFilterEnum
import com.y.app.features.home.data.models.bodies.PostLikeBody
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
            is HomeEvent.LikePost -> likePost(event)
            is HomeEvent.RefreshPosts -> refreshPosts(event)
        }
    }

    private fun likePost(event: HomeEvent.LikePost) {
        viewModelScope.launch {
            postRepository.likePost(PostLikeBody(event.userId, event.postId))
                .collect(onSuccess = { /** do nothing **/ },
                    onError = { message -> _state.update { it.copy(errorMessage = message) } })
        }
    }

    private fun refreshPosts(event: HomeEvent.RefreshPosts) {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = event.visible) }
            postRepository.getPosts(event.filter, event.userId).collect(
                onSuccess = { posts ->
                    _state.update {
                        it.copy(
                            posts = posts.sortByDate(), isRefreshing = false
                        )
                    }
                },
                onError = { message ->
                    _state.update {
                        it.copy(
                            errorMessage = message, isRefreshing = false
                        )
                    }
                },
            )
        }
    }

    private fun getPostsAndUser(filter: PostFilterEnum) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            postRepository.getUser().collect { user ->
                if (user != null) _state.update { it.copy(user = user) }
                postRepository.getPosts(filter, user?.id ?: -1).collect(
                    onSuccess = { posts ->
                        _state.update { it.copy(posts = posts.sortByDate(), isLoading = false) }
                    },
                    onError = { message ->
                        _state.update {
                            it.copy(
                                errorMessage = message, isLoading = false
                            )
                        }
                    },
                )
            }
        }
    }
}