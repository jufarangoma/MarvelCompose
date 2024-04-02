package com.jufarangoma.marvelcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jufarangoma.marvelcompose.presentation.ui.screens.FirstScreen

@Composable
fun MarvelComposeNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.FirstScreen.name
    ) {
        composable(Screens.FirstScreen.name) {
            FirstScreen { route ->
                navController.navigate(route)
            }
        }
    }
}