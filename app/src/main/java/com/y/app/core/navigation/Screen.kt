package com.y.app.core.navigation


sealed class Screen(val route: String) {

    data object LoginScreen : Screen("LoginScreen")

    data object RegistrationScreen : Screen("RegistrationScreen")

    data object HomeScreen : Screen("HomeScreen")

    data object PostDetailsScreen : Screen("PostDetailsScreen")
}