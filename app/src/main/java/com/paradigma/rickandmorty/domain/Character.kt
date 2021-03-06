package com.paradigma.rickandmorty.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class Character @Inject constructor (
    val id: Int,
    val name: String,
    val image: String,
    val gender: String,
    val type: String,
    val status: String,
    val locationId: String,
) : Parcelable
