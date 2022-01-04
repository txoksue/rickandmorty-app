package com.paradigma.rickyandmorty

import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterResponse
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.util.LoaderCharactersData
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeCharacterRepository @Inject constructor(var characterMapper: Mapper<CharacterDTO, Character>) : CharacterRepository, FakeRepository<List<Character>>{

    private var charactersData: CharacterResponse? = null
    private var shouldReturnError: Boolean = false

    override suspend fun getCharacters(page: Int): ResultCharacters {

        if (shouldReturnError){
            return ResultCharacters.Error(Exception("Error getting characters"))
        }

        return charactersData?.results?.let { characterList->
                ResultCharacters.Success(characterList.map { item -> characterMapper.mapToDomain(item) }, 42)

        } ?: ResultCharacters.NoData
    }


    override fun setData(data: List<Character>?) {
        runBlocking {
            charactersData = LoaderCharactersData.load()
        }
    }



    override fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override fun clearData() {
        charactersData = null
    }


    override fun getData() = charactersData?.results?.map{ item -> characterMapper.mapToDomain(item)}?: arrayListOf()

}