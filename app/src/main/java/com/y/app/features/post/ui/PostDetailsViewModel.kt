package com.y.app.features.post.ui

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.app.core.local.DataStoreManager
import com.y.app.features.common.extensions.sortByDate
import com.y.app.features.home.data.PostRepository
import com.y.app.features.home.data.models.bodies.PostLikeBody
import com.y.app.features.post.data.CommentBody
import com.y.app.features.post.data.CommentLikeBody
import com.y.app.features.post.utils.CommentWebSocketManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.hildan.krossbow.websocket.WebSocketException
import java.net.SocketException

class PostDetailsViewModel(
    private val postRepository: PostRepository,
    private val dataStore: DataStoreManager,
    private val commentWebSocketManager: CommentWebSocketManager
) : ViewModel() {

    private val _state: MutableStateFlow<PostDetailsState> = MutableStateFlow(PostDetailsState())
    val state get() = _state.asStateFlow()

    private var webSocketListenerJob: Job? = null

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            dataStore.user.collect { user ->
                _state.update { it.copy(user = user) }
            }
        }
    }

    fun invokeEvent(event: PostDetailsEvent) {
        when (event) {
            is PostDetailsEvent.AddComment -> addComment(event)
            is PostDetailsEvent.GetComments -> getComments(event.postId)
            is PostDetailsEvent.LikeComment -> likeComment(event.commentId)
            is PostDetailsEvent.LikePost -> likePost(event.postId)
        }
    }

    private fun likePost(postId: Int) {
        viewModelScope.launch {
            val userId = state.value.user?.id
            if (userId != null) postRepository.likePost(PostLikeBody(userId, postId)).collect(
                onSuccess = { /** Do nothing **/ }, onError = ::onError
            )
        }
    }

    private fun addComment(event: PostDetailsEvent.AddComment) {
        viewModelScope.launch {
            _state.update { it.copy(isAddCommentLoading = true) }
            dataStore.user.collect { user ->
                if (user != null) postRepository.addComment(
                    event.postId, CommentBody(event.content, user.id)
                ).collect(
                    onSuccess = {
                        _state.update {
                            it.copy(
                                isAddCommentLoading = false,
                            )
                        }
                    },
                    onError = ::onError,
                )
            }
        }
    }

    private fun getComments(postId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            dataStore.user.collect { user ->
                if (user != null) {
                    postRepository.getComments(postId, user.id).collect(
                        onSuccess = { comments ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    comments = comments.sortByDate().toMutableStateList()
                                )
                            }
                            listenOnCommentsChange(postId, user.id)
                        },
                        onError = ::onError,
                    )
                }
            }
        }
    }

    private fun listenOnCommentsChange(postId: Int, userId: Int) {
        webSocketListenerJob?.cancel()
        webSocketListenerJob = viewModelScope.launch {
            commentWebSocketManager.start(postId, onCommentReceived = { comment ->
                _state.update {
                    it.copy(comments = _state.value.comments?.apply {
                        add(0, comment.copy(isNew = comment.author.id != userId))
                    })
                }
            }, onFailure = { e ->
                when (e) {
                    is WebSocketException,
                    is SocketException -> {
                        delay(2000)
                        listenOnCommentsChange(postId, userId)
                    }

                    is CancellationException -> {
                        /** Do nothing **/
                    }

                    else -> onError(e.message)
                }
                e.printStackTrace()
            })
        }
    }

    private fun likeComment(commentId: Int) {
        viewModelScope.launch {
            dataStore.user.collect { user ->
                if (user != null) {
                    postRepository.likeComment(CommentLikeBody(user.id, commentId)).collect(
                        onSuccess = { /** Do nothing **/ }, onError = ::onError
                    )
                }
            }
        }
    }

    private fun onError(message: String?) {
        _state.update {
            it.copy(errorMessage = message, isLoading = false, isAddCommentLoading = false)
        }
    }

    override fun onCleared() {
        webSocketListenerJob?.cancel()
        super.onCleared()
    }

}