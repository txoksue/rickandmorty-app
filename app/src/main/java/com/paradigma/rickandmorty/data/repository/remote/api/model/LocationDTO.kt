package com.paradigma.rickandmorty.data.repository.remote.api.model


import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class LocationDTO @Inject constructor(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("dimension")
    val dimension: String?,
    @SerializedName("residents")
    val residents: List<String>?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("created")
    val created: String?
)