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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.R
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
import com.y.app.features.home.ui.components.EmptyHomeScreen
import com.y.app.features.home.ui.components.PostItem
import com.y.app.posts
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(navigator: Navigator = koinInject(), viewModel: HomeViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value
    if (state.isLoading) {
        DefaultLoadingScreen()
    } else if (!state.posts.isNullOrEmpty() && state.user != null) {
        HomeScreenContent(
            state, { event: HomeEvent -> viewModel.invokeEvent(event) }, navigator
        )
    } else if (state.posts?.isEmpty() == true && state.user != null) {
        Column {
            HomeTopBar(navigator = navigator, user = state.user)
            EmptyHomeScreen(stringResource(R.string.empty_home_text))
        }
    }
    HomeFloatingActionButton(navigator)
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
    val posts = remember { mutableStateListOf(*inputPosts.toTypedArray()) }
    LaunchedEffect(inputPosts) {
        posts.clear()
        posts.addAll(inputPosts)
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
                    }, { userId: Int ->
                        navigator.navigateTo(
                            Screen.ProfileScreen, userId.toString()
                        )
                    }, {
                        onLikeClicked(invokeEvent, post, user.id, posts)
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
}

@Composable
private fun HomeFloatingActionButton(navigator: Navigator) {
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
    invokeEvent: (HomeEvent) -> Unit, post: Post, userId: Int, posts: SnapshotStateList<Post>
) {
    invokeEvent(HomeEvent.LikePost(post.id, userId))
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
                HomeState(posts = posts, user = author1), { }, Navigator()
            )
        }
    }
}