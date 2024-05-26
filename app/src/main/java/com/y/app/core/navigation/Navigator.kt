package com.y.app.core.navigation

import androidx.navigation.NavHostController

class Navigator {
    private lateinit var navController: NavHostController

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }

    fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}