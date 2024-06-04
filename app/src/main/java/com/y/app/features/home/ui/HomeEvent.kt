package com.y.app.features.home.ui

import com.y.app.features.home.data.models.PostFilterEnum

sealed class HomeEvent {
    data class GetPosts(val filter: PostFilterEnum) : HomeEvent()
    data class RefreshPosts(val filter: PostFilterEnum) : HomeEvent()
    data class LikePost(val userId: Int, val postId: Int) : HomeEvent()
}