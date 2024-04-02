package com.jufarangoma.marvelcompose.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.marvelcompose.domain.repositories.ComicsRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val comicsRepository: ComicsRepository,
    private val _comicsState: MutableStateFlow<ComicStates>,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val comicsState: StateFlow<ComicStates> = _comicsState

    fun getSuperHeroComics(characterId: Long) {
        comicsRepository.getComics(characterId).map { result ->
            result.fold(
                onSuccess = {comics ->
                    _comicsState.update {
                        ComicStates.Success(comics)
                    }
                },
                onFailure = { exception ->
                    _comicsState.update {
                        ComicStates.Error(exception.message)
                    }
                }
            )
        }.onStart {
            _comicsState.update { ComicStates.Loading}
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}