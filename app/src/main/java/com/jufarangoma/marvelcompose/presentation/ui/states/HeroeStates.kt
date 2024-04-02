package com.jufarangoma.marvelcompose.presentation.ui.states

import com.jufarangoma.marvelcompose.domain.entities.Hero

sealed class HeroeStates {
    object Loading: HeroeStates()
    class Success(val heroes: List<Hero>): HeroeStates()
    class Error: HeroeStates()
}