package com.jufarangoma.marvelcompose.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.marvelcompose.domain.repositories.ComicsRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ComicsViewModel @Inject constructor(
    private val comicsRepository: ComicsRepository,
    private val _comicsState: MutableStateFlow<ComicStates>,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val comicsState: StateFlow<ComicStates> = _comicsState

    fun getComics(characterId: Long) {
        comicsRepository.getComics(characterId).map { result ->
            result.fold(
                onSuccess = { comics ->
                    _comicsState.value = ComicStates.Success(comics)

                },
                onFailure = { exception ->
                    _comicsState.value = ComicStates.Error(exception.message)

                }
            )
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}