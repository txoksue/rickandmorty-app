package com.paradigma.rickandmorty.data.repository.remote.api.model


import com.google.gson.annotations.SerializedName


data class CharacterLocationDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)