package com.jufarangoma.marvelcompose.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jufarangoma.marvelcompose.domain.repositories.ComicDetailRepository
import com.jufarangoma.marvelcompose.presentation.ui.states.ComicDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ComicDetailViewModel @Inject constructor(
    private val comicDetailRepository: ComicDetailRepository,
    private val _comicDetailState: MutableStateFlow<ComicDetailState>,
    private val coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    val comicDetailState: StateFlow<ComicDetailState> = _comicDetailState

    fun getComicDetail(comicId: Long) {
        comicDetailRepository.getComicDetail(comicId).map { result ->
            result.fold(
                onSuccess = {
                    _comicDetailState.value = ComicDetailState.Success(it)
                },
                onFailure = {
                    _comicDetailState.value = ComicDetailState.Error
                }
            )
        }.flowOn(coroutineDispatcher).launchIn(viewModelScope)
    }
}