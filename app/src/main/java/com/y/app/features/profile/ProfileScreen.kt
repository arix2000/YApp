package com.y.app.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.R
import com.y.app.author3
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.DefaultLoadingScreen
import com.y.app.features.common.DefaultTopBar
import com.y.app.features.common.ErrorBanner
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.ui.components.EmptyScreen
import com.y.app.features.home.ui.components.PostItem
import com.y.app.features.login.data.models.User
import com.y.app.features.profile.ui.ProfileEvent
import com.y.app.features.profile.ui.ProfileViewModel
import com.y.app.features.profile.ui.components.ProfileBanner
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ProfileScreen(
    userId: String,
    viewModel: ProfileViewModel = koinViewModel(),
    navigator: Navigator = koinInject()
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(true) {
        viewModel.invokeEvent(ProfileEvent.GetInitialData(userId.toInt()))
    }

    if (state.isUserDeleted) {
        navigator.navigateToAndClearBackStack(Screen.LoginScreen, Screen.HomeScreen)
    }

    if (state.posts != null && state.user != null) {
        ProfileScreenContent(posts = state.posts,
            user = state.user,
            navigator = navigator,
            isOwnProfile = state.isOwnProfile,
            invokeEvent = { viewModel.invokeEvent(it) })
    } else if (state.isLoading) {
        DefaultLoadingScreen()
    }

    ErrorBanner(errorMessage = state.errorMessage)
}

@Composable
private fun ProfileScreenContent(
    posts: SnapshotStateList<Post>,
    user: User,
    isOwnProfile: Boolean,
    invokeEvent: (ProfileEvent) -> Unit,
    navigator: Navigator
) {
    Column {
        DefaultTopBar(title = "Profile", navigator = navigator, trailingView = {
            if (isOwnProfile) LogOutButton(onLogOutClicked = {
                invokeEvent(ProfileEvent.LogoutUser)
            })
        })
        ProfileBanner(user = user)
        Spacer(modifier = Modifier.height(16.dp))
        if (posts.isNotEmpty()) LazyColumn {
            items(posts) { post ->
                PostItem(post = post,
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                    onPostClicked = {
                        navigator.navigateToPostDetails(post)
                    },
                    onProfileClicked = { userId ->
                        if (user.id != userId) navigator.navigateTo(
                            Screen.ProfileScreen, userId.toString()
                        )
                    },
                    onLikeClicked = { onLikeClicked(invokeEvent, post, user.id, posts) })
            }
        } else Box(modifier = Modifier.height(400.dp)) {
            EmptyScreen(stringResource(R.string.no_post_yet_text))
        }
    }
}

@Composable
private fun LogOutButton(onLogOutClicked: () -> Unit) {
    Row(modifier = Modifier
        .clip(CircleShape)
        .clickable { onLogOutClicked() }
        .padding(8.dp)) {
        Text(text = stringResource(R.string.log_out))
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.AutoMirrored.Default.Logout, contentDescription = "logout"
        )
    }
}

private fun onLikeClicked(
    invokeEvent: (ProfileEvent) -> Unit, post: Post, userId: Int, posts: SnapshotStateList<Post>
) {
    invokeEvent(ProfileEvent.LikePost(post.id, userId))
    val index = posts.indexOf(post)
    if (index != -1) {
        posts[index] = post.copy(
            isLikedByMe = !post.isLikedByMe,
            likesCount = if (!post.isLikedByMe) post.likesCount.inc() else post.likesCount.dec()
        )
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    YTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ProfileScreenContent(
                listOf<Post>().toMutableStateList(), author3, true, {}, Navigator()
            )
        }
    }
}