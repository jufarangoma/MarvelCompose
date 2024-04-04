package com.jufarangoma.marvelcompose.data.repositories

import com.jufarangoma.marvelcompose.data.local.LocalProviderHeroes
import com.jufarangoma.marvelcompose.data.remote.MarvelApi
import com.jufarangoma.marvelcompose.domain.entities.Hero
import com.jufarangoma.marvelcompose.domain.repositories.DomainExceptionStrategy
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class HeroesRepositoryImpl(
    private val marvelApi: MarvelApi,
    private val localProviderHeroes: LocalProviderHeroes,
    private val domainExceptionStrategy: DomainExceptionStrategy
): HeroesRepository {

    override fun getHeroes(heroName: String?): Flow<Result<List<Hero>>> = flow {
        val heroes = heroName?.let {
            val heroesResponse = marvelApi.getHeroes(it)
            heroesResponse.data?.results?.map { it.toDomain() }
        } ?: run {
            localProviderHeroes.getHeroes()
        }
        emit(Result.success(heroes))
    }.catch {
        emit(Result.failure(domainExceptionStrategy.manageError(it)))
    }
}