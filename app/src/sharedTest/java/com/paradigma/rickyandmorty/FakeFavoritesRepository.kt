package com.paradigma.rickyandmorty

import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.ResultFavorites
import com.paradigma.rickyandmorty.domain.Character
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeFavoritesRepository @Inject constructor() : FavoritesRepository {

    private val favoriteCharacters: MutableList<Character> = mutableListOf()
    private var shouldReturnError = false

    override suspend fun getAllFavoriteCharacters(): ResultFavorites {
        if (shouldReturnError) {
            return ResultFavorites.Error(Exception("Error getting favorite characters"))
        }

        return if (favoriteCharacters.isNotEmpty()) {
            ResultFavorites.Success(favoriteCharacters)
        } else {
            ResultFavorites.NoData
        }

    }

    override suspend fun saveCharacter(character: Character) {
        favoriteCharacters.add(character)
    }

    override suspend fun deleteCharacter(character: Character) {
        favoriteCharacters.remove(character)

    }

     fun setData(data: List<Character>?): Unit =
        runBlocking {
            data?.forEach { character ->
                favoriteCharacters.add(character)
            }
        }

    fun getData(): List<Character> = favoriteCharacters

    fun setError(showError: Boolean) {
        shouldReturnError = showError
    }
}
