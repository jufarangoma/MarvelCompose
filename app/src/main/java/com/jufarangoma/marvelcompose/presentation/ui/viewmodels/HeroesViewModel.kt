package com.jufarangoma.marvelcompose.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.HeroStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HeroesViewModel @Inject constructor(
    private val heroesRepository: HeroesRepository,
    private val _heroesState: MutableStateFlow<HeroStates>,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val heroStates: StateFlow<HeroStates> = _heroesState

    fun getHeroes(hero: String? = null) {
        heroesRepository.getHeroes(hero).map {
            it.fold(
                onSuccess = {
                    _heroesState.value = HeroStates.Success(it)
                },
                onFailure = {
                    _heroesState.value = HeroStates.Error
                }
            )
        }.onStart {
            _heroesState.value = HeroStates.Loading
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}