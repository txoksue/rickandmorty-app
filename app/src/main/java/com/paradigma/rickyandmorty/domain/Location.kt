package com.paradigma.rickyandmorty.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class Location @Inject constructor(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
): Parcelable