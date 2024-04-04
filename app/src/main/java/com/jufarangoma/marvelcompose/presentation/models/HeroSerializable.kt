package com.jufarangoma.marvelcompose.presentation.models

import java.io.Serializable

data class HeroSerializable(
    val id: Long,
    val name: String
) : Serializable