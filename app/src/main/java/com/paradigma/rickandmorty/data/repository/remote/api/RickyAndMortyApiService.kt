package com.paradigma.rickandmorty.data.repository.remote.api


import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterResponse
import com.paradigma.rickandmorty.data.repository.remote.api.model.LocationDTO
import retrofit2.Response


interface RickyAndMortyApiService {

    suspend fun getCharacters(page: Int): Response<CharacterResponse>
    suspend fun getLocation(id: String): Response<LocationDTO>

}