package com.jufarangoma.marvelcompose.presentation.ui.states

import com.jufarangoma.marvelcompose.domain.entities.Comic


sealed class ComicStates {
    object Loading: ComicStates()
    class Success(val comics: List<Comic>): ComicStates()
    class Error(val message: String?): ComicStates()
}