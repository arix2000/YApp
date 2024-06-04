package com.y.app.core.navigation

import androidx.navigation.NavHostController
import com.y.app.features.common.extensions.putArgument

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

    fun navigateToAndClearBackStack(screen: Screen) {
        navController.navigate(screen.route) {
            popUpTo(Screen.LoginScreen.route) { inclusive = true }
        }
    }

    fun popBackStack() {
        navController.popBackStack()
    }
}