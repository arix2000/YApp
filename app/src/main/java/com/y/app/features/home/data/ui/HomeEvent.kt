package com.y.app.features.home.data.ui

import com.y.app.features.home.data.models.PostFilterEnum

sealed class HomeEvent {
    class GetPosts(val filter: PostFilterEnum) : HomeEvent()
}