package com.paradigma.rickandmorty.data.repository.remote.api.model


import com.google.gson.annotations.SerializedName
import javax.inject.Inject


data class CharacterDTO @Inject constructor (
    @SerializedName("created")
    val created: String?,
    @SerializedName("episode")
    val episode: List<String>?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val characterLocation: CharacterLocationDTO?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: OriginDTO?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)
