package com.jufarangoma.marvelcompose.presentation.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jufarangoma.marvelcompose.presentation.navigation.Screens
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroeStates
import com.jufarangoma.marvelcompose.presentation.ui.viewmodels.HeroesViewModel

@Composable
fun HeroesScreen(
    heroesViewModel: HeroesViewModel,
    navigateToScreen: (String) -> Unit
) {
    val heroeStates by heroesViewModel.heroeStates.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        heroesViewModel.getHeroes()
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items((heroeStates as? HeroeStates.Success)?.heroes.orEmpty()) { heroe ->
            HeroeItem(heroe = heroe ) {
                navigateToScreen.invoke(Screens.FirstScreen.name)
            }
        }
    }
}