package com.y.app.features.common.extensions

import com.y.app.features.home.data.models.Post
import com.y.app.features.post.data.Comment

@JvmName("sortPostsByDate")
fun List<Post>.sortByDate(): List<Post> {
    return this.sortedByDescending { it.dateTime }
}

@JvmName("sortCommentsByDate")
fun List<Comment>.sortByDate(): List<Comment> {
    return this.sortedByDescending { it.dateTime }
}