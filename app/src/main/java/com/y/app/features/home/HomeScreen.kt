package com.y.app.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.y.app.author1
import com.y.app.core.navigation.Navigator
import com.y.app.core.theme.YTheme
import com.y.app.features.common.AppTopBar
import com.y.app.features.common.DefaultLoadingScreen
import com.y.app.features.common.ErrorBanner
import com.y.app.features.home.data.models.Post
import com.y.app.features.home.data.ui.HomeEvent
import com.y.app.features.home.data.ui.HomeViewModel
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
            state.user,
            state.posts,
            { event: HomeEvent -> viewModel.invokeEvent(event) }, navigator
        )
    }

    ErrorBanner(errorMessage = state.errorMessage)
}

@Composable
fun HomeScreenContent(
    user: User,
    posts: List<Post>,
    invokeEvent: (HomeEvent) -> Unit,
    navigator: Navigator
) {
    Column {
        AppTopBar(navigator)
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    YTheme {
        Surface {
            HomeScreenContent(author1, posts, { }, Navigator())
        }
    }
}