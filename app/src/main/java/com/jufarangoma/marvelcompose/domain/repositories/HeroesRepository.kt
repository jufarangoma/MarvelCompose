package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.Hero

interface HeroesRepository {
    fun getHeroes(): List<Hero>
}