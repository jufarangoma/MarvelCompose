package com.jufarangoma.marvelcompose.presentation.ui.states

import com.jufarangoma.marvelcompose.domain.entities.Heroe

sealed class HeroeStates {
    object Loading: HeroeStates()
    class Success(val heroes: List<Heroe>): HeroeStates()

    class Error: HeroeStates()
}