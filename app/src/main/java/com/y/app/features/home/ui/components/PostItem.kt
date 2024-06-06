package com.y.app.features.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.YTheme
import com.y.app.features.common.Avatar
import com.y.app.features.common.extensions.toProfileColor
import com.y.app.features.common.extensions.toPx
import com.y.app.features.home.data.models.Post
import com.y.app.posts
import kotlin.math.roundToInt

@Composable
fun PostItem(
    post: Post,
    onPostClicked: () -> Unit,
    onProfileClicked: (userId: Int) -> Unit,
    onLikeClicked: () -> Unit
) {
    val author = post.author
    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()
    BoxWithConstraints(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
        .clip(MaterialTheme.shapes.extraLarge)
        .clickable { onPostClicked() }
        .background(
            MaterialTheme.colorScheme.onSecondary, shape = MaterialTheme.shapes.extraLarge
        )
        .padding(16.dp)) {
        val textLayoutResult: TextLayoutResult = textMeasurer.measure(
            post.content,
            constraints = Constraints(
                maxWidth = (this@BoxWithConstraints.maxWidth - 70.dp).toPx().roundToInt()
            ),
        )
        Column {
            Box {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(
                                    CircleShape
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onProfileClicked(post.author.id)
                                }) {
                            Avatar(
                                firstName = author.name,
                                avatarColor = author.avatarColor.toProfileColor(),
                                avatarSize = 28.dp,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = author.fullName,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1
                            )
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            text = post.getDateTimeDisplayText(context),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = post.content, maxLines = 8)
                }
                if (textLayoutResult.lineCount > 8) TextFadingOverlay(
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }
            CommentAndLikeSection(post, onLikeClicked = onLikeClicked)
        }
    }
}

@Composable
private fun TextFadingOverlay(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onSecondary, Color.Transparent
                    ), startY = Float.POSITIVE_INFINITY, endY = 0f
                )
            )
            .fillMaxWidth()
            .height(100.dp)
    )
}

@Preview
@Composable
private fun PostItemPreview() {
    YTheme {
        Surface(modifier = Modifier) {
            PostItem(posts.first(), {}, {}, {})
        }
    }
}