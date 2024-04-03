package com.jufarangoma.marvelcompose.presentation.ui.states

import com.jufarangoma.marvelcompose.domain.entities.Comic

sealed class ComicDetailState {
    object Loading : ComicDetailState()
    class Success(val comic: Comic) : ComicDetailState()
    object Error : ComicDetailState()
}