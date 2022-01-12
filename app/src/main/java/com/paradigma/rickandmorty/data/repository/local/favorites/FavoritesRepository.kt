package com.paradigma.rickandmorty.data.repository.local.favorites


import com.paradigma.rickandmorty.domain.Character

interface FavoritesRepository {

    suspend fun getAllFavoriteCharacters(): ResultFavorites
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
}