package com.y.app.features.addpost.ui

sealed class AddPostEvent {
    class AddPost(val content: String, val imageUrl: String?): AddPostEvent()
}