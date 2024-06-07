package com.y.app.features.post.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.y.app.features.home.data.models.Comment
import com.y.app.features.login.data.models.User

data class PostDetailsState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isAddCommentLoading: Boolean = false,
    val comments: SnapshotStateList<Comment>? = null,
    val user: User? = null
)