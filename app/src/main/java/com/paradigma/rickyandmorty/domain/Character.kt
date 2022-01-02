package com.paradigma.rickyandmorty.domain

import javax.inject.Inject

data class Character @Inject constructor (
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val location: Location,
    val url: String
)
