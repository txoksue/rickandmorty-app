package com.paradigma.rickyandmorty

import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterResponse
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.util.LoaderCharactersData
import com.paradigma.rickyandmorty.util.LoaderLocationData
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeRickyAndMortyApiService @Inject constructor() : RickyAndMortyApiService {

    private var characterResponse: CharacterResponse? = null
    private var characterLocation: LocationDTO? = null

    override suspend fun getCharacters(page: Int): Response<CharacterResponse>  {
        return Response.success(characterResponse)
    }

    override suspend fun getLocation(id: String): Response<LocationDTO> {

        return characterLocation?.let {
            Response.success(it)
        } ?: Response.error(0, null)

    }


    fun setCharacterData() {
        runBlocking {
            characterResponse = LoaderCharactersData.load()
        }
    }


    fun getCharacterData() = characterResponse?.results



    fun setCharacterLocationData() {
        runBlocking {
            characterLocation = LoaderLocationData.load()
        }
    }


    fun getCharacterLocationData() = characterLocation
}