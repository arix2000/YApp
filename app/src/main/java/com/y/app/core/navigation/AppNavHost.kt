package com.y.app.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.y.app.features.home.HomeScreen
import com.y.app.features.login.LoginScreen
import com.y.app.features.login.RegistrationScreen
import com.y.app.features.post.PostDetailsScreen
import org.koin.compose.koinInject

@Composable
fun AppNavHost() {
    val navController = rememberNavController().apply {
        koinInject<Navigator>().setNavController(this)
    }
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) },
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen()
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationScreen()
        }
        composable(Screen.PostDetailsScreen.route) {
            PostDetailsScreen()
        }
    }
}