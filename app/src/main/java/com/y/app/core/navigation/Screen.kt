package com.y.app.core.navigation

import com.y.app.features.common.extensions.withArgument


sealed class Screen(private val _route: String, val argName: String? = null) {

    val route: String
        get() {
            var finalRoute = _route
            if (argName != null) {
                finalRoute = finalRoute.withArgument(argName)
            }
            return finalRoute
        }

    data object LoginScreen : Screen("LoginScreen")

    data object RegistrationScreen : Screen("RegistrationScreen")

    data object HomeScreen : Screen("HomeScreen")

    data object PostDetailsScreen : Screen("PostDetailsScreen", "postId")

    data object ProfileScreen : Screen("ProfileScreen", "userId")

    data object AddPostScreen : Screen("AddPostScreen")
}