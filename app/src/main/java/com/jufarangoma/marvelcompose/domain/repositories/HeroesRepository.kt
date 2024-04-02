package com.jufarangoma.marvelcompose.domain.repositories

import com.jufarangoma.marvelcompose.domain.entities.Heroe

interface HeroesRepository {
    fun getHeroes(): List<Heroe>
}