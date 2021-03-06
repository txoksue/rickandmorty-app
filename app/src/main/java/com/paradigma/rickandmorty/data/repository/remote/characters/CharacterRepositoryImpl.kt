package com.paradigma.rickandmorty.data.repository.remote.characters

import android.util.Log
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.ResultCharacters
import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(var rickyAndMortyApiService: RickyAndMortyApiService, var characterMapper: Mapper<CharacterDTO, Character>) : CharacterRepository {

    companion object {
        val TAG: String = CharacterRepositoryImpl::class.java.name
    }


    override suspend fun getCharacters(page: Int): ResultCharacters = withContext(Dispatchers.IO) {

        try {

            val response = rickyAndMortyApiService.getCharacters(page)

            if (response.isSuccessful) {

                response.body()?.let { responseCharacter ->

                    val characterList = responseCharacter.results?.map { characterDTO ->
                        characterMapper.mapToDomain(characterDTO)
                    } ?: return@withContext ResultCharacters.NoData

                    val totalPages = responseCharacter.info?.pages ?: return@withContext ResultCharacters.NoData

                    return@withContext ResultCharacters.Success(characterList, totalPages)

                } ?: return@withContext ResultCharacters.NoData

            } else {

                return@withContext ResultCharacters.Error(IOException("Error getting characters"))
            }

        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
            return@withContext ResultCharacters.Error(IOException("Error getting characters", e))
        }

    }

}