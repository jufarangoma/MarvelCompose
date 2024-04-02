package com.jufarangoma.marvelcompose.domain.entities

import okhttp3.HttpUrl

data class Heroe(
    val id: Long,
    val name: String,
    val image: HttpUrl
)
