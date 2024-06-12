package com.y.app.core.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import com.y.app.features.common.extensions.putArgument
import com.y.app.features.home.data.models.Post

class Navigator {
    private lateinit var navController: NavHostController

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun navigateTo(screen: Screen, argument: String) {
        if (screen.argName != null) {
            navController.navigate(
                screen.route.putArgument(screen.argName, argument)
            )
        }
    }

    fun navigateToPostDetails(post: Post) {
        val nodeId = navController.graph.findNode(route = Screen.PostDetailsScreen.route)?.id
        if (nodeId != null) {
            navController.navigate(nodeId, bundleOf(Screen.PostDetailsScreen.argName!! to post))
        }
    }

    fun navigateToAndClearBackStack(screen: Screen, currentScreen: Screen) {
        navController.navigate(screen.route) {
            popUpTo(currentScreen.route) { inclusive = true }
        }
    }

    suspend fun listenOnBackStack(currentScreen: Screen, onBackToScreen: () -> Unit) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            if (backStackEntry.destination.route == currentScreen.route)
                onBackToScreen()
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}