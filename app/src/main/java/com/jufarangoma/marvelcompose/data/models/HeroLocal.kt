package com.jufarangoma.marvelcompose.data.models

import com.jufarangoma.marvelcompose.domain.entities.Hero
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

data class HeroLocal(
    val id: Long,
    val name: String,
    val thumbnail: Thumbnail
) {
    fun toDomain() = Hero(
        id = id,
        name = name,
        image = thumbnail.toDomainThumbnail()
    )
}

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun toDomainThumbnail(): HttpUrl {
        val httpsPath = if (path.startsWith("https")) path
        else path.replaceFirst("http", "https")

        return "$httpsPath.$extension".toHttpUrl()
    }
}