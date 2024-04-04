package com.jufarangoma.marvelcompose.data.remote

import com.jufarangoma.marvelcompose.data.models.ComicDTO
import com.jufarangoma.marvelcompose.data.models.ComicResponse
import com.jufarangoma.marvelcompose.data.models.HeroResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters/{characterId}/comics")
    suspend fun getComics(@Path("characterId") characterId: Long): ComicResponse

    @GET("comics/{comicId}")
    suspend fun getComicDetail(@Path("comicId") comicId: Long): ComicResponse

    @GET("characters")
    suspend fun getHeroes(@Query("nameStartsWith") heroName: String): HeroResponse
}