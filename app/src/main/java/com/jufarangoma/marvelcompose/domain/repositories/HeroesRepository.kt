package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.Hero
import kotlinx.coroutines.flow.Flow

interface HeroesRepository {
    fun getHeroes(heroName: String?): Flow<Result<List<Hero>>>
}