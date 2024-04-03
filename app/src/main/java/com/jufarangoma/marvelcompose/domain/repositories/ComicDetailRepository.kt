package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.Comic
import kotlinx.coroutines.flow.Flow

interface ComicDetailRepository {

    fun getComicDetail(comicId: Long): Flow<Result<Comic>>
}