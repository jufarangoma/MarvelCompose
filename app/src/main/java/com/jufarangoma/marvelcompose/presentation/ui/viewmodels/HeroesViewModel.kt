package com.jufarangoma.marvelcompose.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroeStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository,
    private val _heroesState: MutableStateFlow<HeroeStates>
) : ViewModel(){

    val heroeStates: StateFlow<HeroeStates> = _heroesState

    fun getHeroes() {
        viewModelScope.launch {
            val heroe = heroesRepository.getHeroes()
            _heroesState.update {
                HeroeStates.Success(heroe)
            }
        }
    }
}