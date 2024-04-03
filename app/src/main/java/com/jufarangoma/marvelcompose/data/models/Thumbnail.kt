package com.jufarangoma.marvelcompose.data.models

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

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