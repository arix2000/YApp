package com.y.app.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.YTheme
import com.y.app.features.home.data.models.Post
import com.y.app.posts

@Composable
fun CommentAndLikeSection(post: Post, onLikeClicked: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IndicatorRow {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Comment,
                contentDescription = "comments",
                modifier = Modifier.size(17.dp)
            )
            Text(text = "${post.commentsCount}", fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.width(6.dp))
        LikeIndicator(
            isLiked = post.isLikedByMe,
            onClicked = { onLikeClicked() },
            likesCount = post.likesCount
        )

    }
}

@Preview
@Composable
private fun CommentAndLikeSectionPreview() {
    YTheme {
        Surface {
            CommentAndLikeSection(posts.first()) {}
        }
    }
}