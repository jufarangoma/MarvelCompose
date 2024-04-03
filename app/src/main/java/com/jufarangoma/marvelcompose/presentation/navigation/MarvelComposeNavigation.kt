package com.jufarangoma.marvelcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jufarangoma.marvelcompose.presentation.ui.screens.FirstScreen
import com.jufarangoma.marvelcompose.presentation.ui.screens.comics.ComicsScreen
import com.jufarangoma.marvelcompose.presentation.ui.screens.heroes.HeroesScreen

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
        composable(
            route = Screens.ComicsScreen.name + "/{heroName}/{heroId}",
            arguments = listOf(
                navArgument(name = "heroId") { type = NavType.LongType },
                navArgument(name = "heroName") { type = NavType.StringType }
            )
        ) {
            ComicsScreen(
                comicsViewModel = hiltViewModel(),
                heroId = it.arguments?.getLong("heroId") ?: 0,
                heroName = it.arguments?.getString("heroName") ?: ""
            ) { route ->
                navController.navigate(route)
            }
        }
    }
}