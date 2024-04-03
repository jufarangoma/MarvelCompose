package com.jufarangoma.marvelcompose.data.models

import com.jufarangoma.marvelcompose.domain.entities.Hero

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