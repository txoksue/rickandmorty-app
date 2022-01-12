package com.paradigma.rickandmorty.data.repository.remote.api

import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterResponse
import com.paradigma.rickandmorty.data.repository.remote.api.model.LocationDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyApi {

    @GET("character")
    suspend fun characters(): Response<CharacterResponse>

    @GET("character")
    suspend fun charactersForPage(@Query("page") page: Int): Response<CharacterResponse>

    @GET("location/{location}")
    suspend fun getLocationById(@Path("location") location: String): Response<LocationDTO>
}