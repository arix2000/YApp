package com.y.app.features.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.LikeColor
import com.y.app.core.theme.YTheme
import com.y.app.features.home.data.models.Post
import com.y.app.posts

@Composable
fun CommentAndLikeSection(post: Post, onCommentClicked: () -> Unit, onLikeClicked: () -> Unit) {
    val materialTheme = MaterialTheme.colorScheme
    var likeColor by remember {
        mutableStateOf(if (post.isLikedByMe) LikeColor else materialTheme.onBackground)
    }
    val animatedLikeColor by animateColorAsState(
        targetValue = likeColor,
        label = "AnimatedLikeColor",
        animationSpec = tween(300, easing = LinearEasing)
    )
    DisposableEffect(key1 = post.isLikedByMe) {
        likeColor = if (post.isLikedByMe) LikeColor else materialTheme.onBackground
        onDispose { }
    }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IndicatorRow(onClick = { onCommentClicked() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Comment,
                contentDescription = "comments",
                modifier = Modifier.size(17.dp)
            )
            Text(text = "${post.commentsCount}", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(6.dp))
        IndicatorRow(onClick = { onLikeClicked() }) {
            AnimatedContent(targetState = post.isLikedByMe,
                label = "SizeInSizeOutAnimFavourite",
                transitionSpec = {
                    fadeIn(tween(500)) togetherWith fadeOut(tween(500))
                }) { isLikedByMe ->
                Icon(
                    imageVector = if (isLikedByMe) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "likes",
                    modifier = Modifier.size(17.dp),
                    tint = animatedLikeColor
                )
            }
            Text(text = "${post.likesCount}", fontSize = 14.sp, color = animatedLikeColor)
        }

    }
}

@Composable
private fun IndicatorRow(onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .padding(8.dp)) {
        content()
    }
}

@Preview
@Composable
private fun CommentAndLikeSectionPreview() {
    YTheme {
        Surface {
            CommentAndLikeSection(posts.first(), {}, {})
        }
    }
}