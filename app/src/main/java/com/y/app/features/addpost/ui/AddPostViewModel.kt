package com.y.app.features.addpost.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.models.bodies.PostBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPostViewModel(private val postRepository: PostRepository) : ViewModel() {
    private var _state: MutableStateFlow<AddPostState> = MutableStateFlow(AddPostState())
    val state get() = _state.asStateFlow()

    fun invokeEvent(event: AddPostEvent) {
        when (event) {
            is AddPostEvent.AddPost -> addPost(event)
        }
    }

    private fun addPost(event: AddPostEvent.AddPost) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            postRepository.getUser().collect { user ->
                if (user != null) {
                    postRepository.addPost(PostBody(user.id, event.content, event.imageUrl))
                        .collect(onSuccess = { response ->
                            _state.update {
                                it.copy(
                                    postAdded = response, isLoading = false
                                )
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

        }
    }

}