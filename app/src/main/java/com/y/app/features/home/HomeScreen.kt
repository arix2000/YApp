package com.y.app.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.author1
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.DefaultLoadingScreen
import com.y.app.features.common.ErrorBanner
import com.y.app.features.common.HomeTopBar
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.data.models.PostFilterEnum
import com.y.app.features.home.ui.HomeEvent
import com.y.app.features.home.ui.HomeState
import com.y.app.features.home.ui.HomeViewModel
import com.y.app.features.home.ui.components.PostItem
import com.y.app.features.login.data.models.User
import com.y.app.posts
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(navigator: Navigator = koinInject(), viewModel: HomeViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value
    if (state.isLoading) {
        DefaultLoadingScreen()
    } else if (state.posts != null && state.user != null) {
        HomeScreenContent(
            state, { event: HomeEvent -> viewModel.invokeEvent(event) }, navigator
        )
    }

    ErrorBanner(errorMessage = state.errorMessage)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    state: HomeState, invokeEvent: (HomeEvent) -> Unit, navigator: Navigator
) {
    val user = state.user!!
    val inputPosts = state.posts!!
    val pullRefreshState = rememberPullRefreshState(refreshing = state.isRefreshing, onRefresh = {
        invokeEvent(HomeEvent.RefreshPosts(PostFilterEnum.NEW))
    })
    var posts = remember { mutableStateListOf<Post>() }
    DisposableEffect(inputPosts) {
        posts = inputPosts.toMutableStateList()
        onDispose { }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar(navigator, user)
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
            LazyColumn {
                item { Spacer(modifier = Modifier.height(24.dp)) }
                items(posts) { post ->
                    PostItem(post = post, onPostClicked = {
                        navigator.navigateTo(Screen.PostDetailsScreen, post.id.toString())
                    }, { navigator.navigateTo(Screen.PostDetailsScreen) }, {
                        onLikeClicked(invokeEvent, post, user, posts)
                    })
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
            PullRefreshIndicator(
                refreshing = state.isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
    Box(
        contentAlignment = Alignment.BottomEnd, modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        FloatingActionButton(onClick = { navigator.navigateTo(Screen.AddPostScreen) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add post")
        }
    }
}

private fun onLikeClicked(
    invokeEvent: (HomeEvent) -> Unit, post: Post, user: User, posts: SnapshotStateList<Post>
) {
    invokeEvent(HomeEvent.LikePost(post.id, user.id))
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
private fun HomeScreenPreview() {
    YTheme {
        Surface {
            HomeScreenContent(
                HomeState(isRefreshing = false, posts = posts, user = author1), { }, Navigator()
            )
        }
    }
}