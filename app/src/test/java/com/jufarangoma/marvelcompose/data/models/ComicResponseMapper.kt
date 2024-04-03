package com.jufarangoma.marvelcompose.data.models

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Test

class ComicResponseMapper {

    @Test
    fun mapToDomain(){
        val comicResponse = ComicResponse(
            DataResponse(
                listOf(
                    ComicDTO(
                        1L,
                        "title",
                        "description",
                        Thumbnail("http://path", "jpg")
                    )
                )
            )
        )

        val result = comicResponse.data?.results?.map { it.toDomainComic() }

        result?.first()?.apply {
            assert(id == 1L)
            assert(title == "title")
            assert(image == "https://path.jpg".toHttpUrl())
        }
    }
}