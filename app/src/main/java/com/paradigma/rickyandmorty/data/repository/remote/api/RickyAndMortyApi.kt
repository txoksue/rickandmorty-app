package com.paradigma.rickyandmorty.data.repository.remote.api

import android.location.Location
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterResponse
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
    suspend fun getLocationById(@Path("location") location: String): Response<Location>
}