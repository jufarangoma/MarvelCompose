package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.Comic
import com.jufarangoma.marvelcompose.domain.repositories.ComicsRepository
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ComicsRepositoryImpl(
    private val marvelApi: MarvelApi,
    private val domainExceptionStrategy: DomainExceptionStrategy
) : ComicsRepository{
    override fun getComics(heroId: Long): Flow<Result<List<Comic>>> =
        flow {
            val comicResponse = marvelApi.getComics(heroId)
            val comics = comicResponse.data.results.map { it.toDomainComic() }
            emit(Result.success(comics))
        }.catch {
            emit(Result.failure(domainExceptionStrategy.manageError(it)))
        }

}