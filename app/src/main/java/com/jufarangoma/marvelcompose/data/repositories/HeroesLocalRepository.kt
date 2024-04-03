package com.jufarangoma.marvelcompose.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jufarangoma.marvelcompose.data.models.HeroLocal
import com.jufarangoma.marvelcompose.domain.entities.Hero
import com.jufarangoma.marvelcompose.domain.repositories.HeroesRepository
import java.lang.reflect.Type

class HeroesLocalRepository(
    private val context: Context
): HeroesRepository {

    override fun getHeroes(): List<Hero> {
        val listType: Type = object : TypeToken<List<HeroLocal?>?>() {}.type
        val objectArrayString: String = context.assets.open("menu.json").bufferedReader().use { it.readText() }
        val objectArray: List<HeroLocal?> = Gson().fromJson(objectArrayString, listType)
        return objectArray.mapNotNull { it?.toDomain() }
    }
}