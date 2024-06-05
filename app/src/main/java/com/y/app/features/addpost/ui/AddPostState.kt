package com.y.app.features.addpost.ui

data class AddPostState(
    val postAdded: Unit? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)