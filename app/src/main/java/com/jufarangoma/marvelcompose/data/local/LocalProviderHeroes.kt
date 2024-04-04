package com.jufarangoma.marvelcompose.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jufarangoma.marvelcompose.data.models.HeroLocal
import com.jufarangoma.marvelcompose.domain.entities.Hero
import java.lang.reflect.Type

class LocalProviderHeroes(
    val context: Context
) {
    fun getHeroes(): List<Hero> {
        val listType: Type = object : TypeToken<List<HeroLocal?>?>() {}.type
        val objectArrayString: String = context.assets.open("menu.json").bufferedReader().use { it.readText() }
        val objectArray: List<HeroLocal?> = Gson().fromJson(objectArrayString, listType)
        return objectArray.mapNotNull { it?.toDomain() }
    }
}