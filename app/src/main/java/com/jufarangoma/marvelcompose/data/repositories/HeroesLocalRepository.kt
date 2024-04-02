package com.jufarangoma.marvelcompose.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jufarangoma.marvelcompose.data.models.HeroeLocal
import com.jufarangoma.marvelcompose.domain.entities.Heroe
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import java.lang.reflect.Type

class HeroesLocalRepository(
    private val context: Context
): HeroesRepository {

    override fun getHeroes(): List<Heroe> {
        val listType: Type = object : TypeToken<List<HeroeLocal?>?>() {}.type
        val objectArrayString: String = context.assets.open("menu.json").bufferedReader().use { it.readText() }
        val objectArray: List<HeroeLocal?> = Gson().fromJson(objectArrayString, listType)
        return objectArray.mapNotNull { it?.toDomain() }
    }
}