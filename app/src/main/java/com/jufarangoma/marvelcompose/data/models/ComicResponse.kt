package com.jufarangoma.marvelcompose.data.models

import com.jufarangoma.marvelcompose.domain.entities.Comic

data class ComicResponse(
    val data: DataResponse
)

data class DataResponse(
    val results: List<ComicDTO>
)

data class ComicDTO(
    val title: String,
    val description: String,
    val thumbnail: Thumbnail
) {
    fun toDomainComic() = Comic(
        title = title,
        description = description,
        image = thumbnail.toDomainThumbnail()
    )
}
