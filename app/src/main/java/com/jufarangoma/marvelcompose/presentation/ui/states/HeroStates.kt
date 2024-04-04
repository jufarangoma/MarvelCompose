package com.jufarangoma.marvelcompose.presentation.ui.states

import com.jufarangoma.marvelcompose.domain.entities.Hero

sealed class HeroStates {
    object Loading : HeroStates()
    class Success(val heroes: List<Hero>) : HeroStates()
    object Error : HeroStates()
}