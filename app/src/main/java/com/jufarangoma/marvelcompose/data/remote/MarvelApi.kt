package com.jufarangoma.marvelcompose.data.remote

import com.jufarangoma.marvelcompose.data.models.ComicResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {

    @GET("characters/{characterId}/comics")
    suspend fun getComics(@Path("characterId") characterId: Long): ComicResponse

}