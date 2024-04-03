package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.models.EmptyComic
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.Comic
import com.jufarangoma.marvelcompose.domain.repositories.ComicDetailRepository
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ComicDetailRepositoryImpl(
    private val marvelApi: MarvelApi,
    private val domainExceptionStrategy: DomainExceptionStrategy
) : ComicDetailRepository {
    override fun getComicDetail(comicId: Long): Flow<Result<Comic>> =
        flow {
            val comicResponse = marvelApi.getComicDetail(comicId)
            val comic = comicResponse.data?.results?.first()?.toDomainComic() ?: throw EmptyComic
            emit(Result.success(comic))
        }.catch {
            emit(Result.failure(domainExceptionStrategy.manageError(it)))
        }
}
