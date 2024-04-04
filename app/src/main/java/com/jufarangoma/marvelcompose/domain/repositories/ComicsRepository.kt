package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.Comic
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getComics(heroId: Long): Flow<Result<List<Comic>>>
}