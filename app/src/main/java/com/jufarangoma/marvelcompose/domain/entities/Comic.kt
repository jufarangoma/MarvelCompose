package com.jufarangoma.marvelcompose.domain.entities

import okhttp3.HttpUrl

data class Comic(
    val id: Long,
    val title: String,
    val description: String?,
    val image: HttpUrl?
)
