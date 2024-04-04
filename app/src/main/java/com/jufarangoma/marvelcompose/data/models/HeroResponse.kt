package com.jufarangoma.marvelcompose.data.models

import com.jufarangoma.marvelcompose.domain.entities.Hero

data class HeroResponse (
    val data: DataHeroResponse?
)

data class DataHeroResponse(
    val results: List<HeroDTO>?
)

data class HeroDTO(
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