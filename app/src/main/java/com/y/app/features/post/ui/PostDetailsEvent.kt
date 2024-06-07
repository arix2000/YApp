package com.y.app.features.post.ui

sealed class PostDetailsEvent {
    data class GetComments(val postId: Int): PostDetailsEvent()
    data class AddComment(val postId: Int, val content: String): PostDetailsEvent()
    data class LikeComment(val commentId: Int): PostDetailsEvent()
    data class LikePost(val postId: Int): PostDetailsEvent()

}