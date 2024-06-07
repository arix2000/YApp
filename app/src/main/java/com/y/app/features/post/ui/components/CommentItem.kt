package com.y.app.features.post.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.comments
import com.y.app.core.theme.YTheme
import com.y.app.features.home.data.models.Comment
import com.y.app.features.home.ui.components.AvatarHeader
import com.y.app.features.home.ui.components.LikeIndicator

@Composable
fun CommentItem(
    comment: Comment, onLikeClicked: () -> Unit, onProfileClicked: (userId: Int) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .background(
                MaterialTheme.colorScheme.onSecondary,
                MaterialTheme.shapes.extraLarge
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AvatarHeader(user = comment.author) {
                onProfileClicked(comment.author.id)
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = comment.getDateTimeDisplayText(context),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = comment.content)
        LikeIndicator(
            isLiked = comment.isLikedByMe,
            onClicked = { onLikeClicked() },
            likesCount = comment.likesCount,
            modifier = Modifier.align(Alignment.End)
        )
    }

}

@Preview
@Composable
private fun CommentItemPreview() {
    YTheme {
        Surface {
            CommentItem(comments.first(), onLikeClicked = {}, onProfileClicked = {})
        }
    }
}