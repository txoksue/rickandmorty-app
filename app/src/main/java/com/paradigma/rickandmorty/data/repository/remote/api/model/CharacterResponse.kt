package com.paradigma.rickandmorty.data.repository.remote.api.model


import com.google.gson.annotations.SerializedName


data class CharacterResponse(
    @SerializedName("info")
    val info: InfoDTO?,
    @SerializedName("results")
    val results: List<CharacterDTO>?
)