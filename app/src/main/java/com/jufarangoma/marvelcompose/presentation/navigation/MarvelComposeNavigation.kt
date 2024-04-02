package com.jufarangoma.marvelcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jufarangoma.marvelcompose.presentation.ui.screens.FirstScreen
import com.jufarangoma.marvelcompose.presentation.ui.screens.HeroesScreen

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
        composable(Screens.HeroesScreen.name) {
            HeroesScreen(heroesViewModel = hiltViewModel()) { route ->
                navController.navigate(route)
            }
        }
    }
}