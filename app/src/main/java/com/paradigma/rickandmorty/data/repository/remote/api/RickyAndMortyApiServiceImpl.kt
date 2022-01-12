package com.paradigma.rickandmorty.data.repository.remote.api

import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterResponse
import com.paradigma.rickandmorty.data.repository.remote.api.model.LocationDTO
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RickyAndMortyApiServiceImpl @Inject constructor(var rickyAndMortyApi: RickyAndMortyApi): RickyAndMortyApiService {


    override suspend fun getCharacters(page: Int): Response<CharacterResponse> {
        return rickyAndMortyApi.charactersForPage(page)
    }

    override suspend fun getLocation(id: String): Response<LocationDTO> {
        return rickyAndMortyApi.getLocationById(id)
    }


}