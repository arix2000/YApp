package com.y.app.features.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.arix.pokedex.features.pokemon_details.presentation.ui.components.full_screen_image_dialog.FullScreenImageDialog
import com.y.app.R
import com.y.app.core.theme.YTheme
import com.y.app.features.common.extensions.toPx
import com.y.app.features.home.data.models.Post
import com.y.app.posts
import kotlin.math.roundToInt

@Composable
fun PostItem(
    post: Post,
    onPostClicked: () -> Unit,
    onProfileClicked: (userId: Int) -> Unit,
    onLikeClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isPostClickEnabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraLarge,
    backgroundColor: Color = MaterialTheme.colorScheme.onSecondary,
    contentMaxLines: Int = 8,
) {
    val author = post.author
    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()
    val isDialogShowed = remember { mutableStateOf(false) }
    BoxWithConstraints(modifier = modifier
        .fillMaxWidth()
        .clip(shape)
        .clickable(isPostClickEnabled) { onPostClicked() }
        .background(
            backgroundColor, shape = shape
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
                        AvatarHeader(user = author) {
                            onProfileClicked(post.author.id)
                        }
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            text = post.getDateTimeDisplayText(context),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = post.content, maxLines = contentMaxLines)
                }
                if (textLayoutResult.lineCount > contentMaxLines) TextFadingOverlay(
                    modifier = Modifier.align(
                        Alignment.BottomCenter
                    )
                )
            }
            if (!post.imageUrl.isNullOrBlank()) ImageSection(
                post.imageUrl,
                onClick = { isDialogShowed.value = true })
            CommentAndLikeSection(post, onLikeClicked = onLikeClicked)
        }
    }
    FullScreenImageDialog(isDialogShowed = isDialogShowed, imageUrl = post.imageUrl ?: "")
}

@Composable
fun ImageSection(imageUrl: String, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    AsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.preview_image),
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .heightIn(100.dp, 250.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        contentScale = ContentScale.FillWidth,
    )
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