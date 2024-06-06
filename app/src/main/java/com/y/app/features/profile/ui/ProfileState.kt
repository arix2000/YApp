package com.y.app.features.profile.ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.y.app.features.home.data.models.Post
import com.y.app.features.login.data.models.User

data class ProfileState(
    val isLoading: Boolean = false,
    val posts: SnapshotStateList<Post>? = null,
    val user: User? = null,
    val errorMessage: String? = null,
    val isOwnProfile: Boolean = false,
    val isUserDeleted: Boolean = false,
)