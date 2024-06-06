package com.y.app.features.profile.ui

sealed class ProfileEvent {
    data class LikePost(val userId: Int, val postId: Int) : ProfileEvent()
    data class GetInitialData(val userId: Int): ProfileEvent()
    data object LogoutUser: ProfileEvent()
}