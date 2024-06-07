package com.y.app.features.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CommentsDisabled
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.R
import com.y.app.author1
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.DefaultLoadingScreen
import com.y.app.features.common.DefaultTopBar
import com.y.app.features.common.ErrorBanner
import com.y.app.features.home.data.models.Comment
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.ui.components.EmptyScreen
import com.y.app.features.home.ui.components.PostItem
import com.y.app.features.post.ui.PostDetailsEvent
import com.y.app.features.post.ui.PostDetailsState
import com.y.app.features.post.ui.PostDetailsViewModel
import com.y.app.features.post.ui.components.AddCommentSection
import com.y.app.features.post.ui.components.CommentItem
import com.y.app.posts
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun PostDetailsScreen(
    post: Post,
    viewModel: PostDetailsViewModel = koinViewModel(),
    navigator: Navigator = koinInject()
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(true) {
        viewModel.invokeEvent(PostDetailsEvent.GetComments(post.id))
    }
    when {
        state.isLoading -> DefaultLoadingScreen()
        state.user != null && state.comments != null -> PostDetailsScreenContent(
            post, state, viewModel::invokeEvent, navigator
        )
    }
    ErrorBanner(errorMessage = state.errorMessage)
}

@Composable
private fun PostDetailsScreenContent(
    post: Post,
    state: PostDetailsState,
    invokeEvent: (PostDetailsEvent) -> Unit,
    navigator: Navigator,
) {
    var mutablePost by remember { mutableStateOf(post) }
    Box(modifier = Modifier.imePadding()) {
        Column {
            DefaultTopBar(
                title = stringResource(R.string.post_details_title, post.author.name),
                navigator = navigator
            )
            LazyColumn {
                item {
                    PostItem(
                        post = mutablePost,
                        onPostClicked = {},
                        onProfileClicked = { userId: Int ->
                            navigator.navigateTo(Screen.ProfileScreen, userId.toString())
                        },
                        onLikeClicked = {
                            invokeEvent(PostDetailsEvent.LikePost(mutablePost.id))
                            mutablePost = mutablePost.copyWithLikeChange()
                        },
                        isPostClickEnabled = false,
                        shape = RoundedCornerShape(
                            bottomEnd = 50.dp, bottomStart = 50.dp
                        ),
                        contentMaxLines = Int.MAX_VALUE,
                        backgroundColor = Color.Transparent
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                if (!state.comments.isNullOrEmpty()) items(state.comments) { comment ->
                    CommentItem(comment = comment,
                        onLikeClicked = { onLikeClicked(invokeEvent, comment, state.comments) },
                        onProfileClicked = { userId: Int ->
                            navigator.navigateTo(Screen.ProfileScreen, userId.toString())
                        })
                }
                else item {
                    EmptyScreen(
                        text = stringResource(R.string.no_comments_yet_text),
                        icon = Icons.Default.CommentsDisabled
                    )
                }
                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
        AddCommentSection(state.user!!, onAddClicked = {
            mutablePost = mutablePost.copy(commentsCount = mutablePost.commentsCount.inc())
            invokeEvent(PostDetailsEvent.AddComment(post.id, it))
        })
    }
}

private fun onLikeClicked(
    invokeEvent: (PostDetailsEvent) -> Unit, comment: Comment, comments: SnapshotStateList<Comment>
) {
    invokeEvent(PostDetailsEvent.LikeComment(comment.id))
    val index = comments.indexOf(comment)
    if (index != -1) {
        comments[index] = comment.copy(
            isLikedByMe = !comment.isLikedByMe,
            likesCount = if (!comment.isLikedByMe) comment.likesCount.inc() else comment.likesCount.dec()
        )
    }
}

@Preview
@Composable
private fun PostDetailsScreenPreview() {
    YTheme {
        Surface {
            PostDetailsScreenContent(
                posts.first(),
                PostDetailsState(comments = listOf<Comment>().toMutableStateList(), user = author1),
                { },
                Navigator()
            )
        }
    }
}