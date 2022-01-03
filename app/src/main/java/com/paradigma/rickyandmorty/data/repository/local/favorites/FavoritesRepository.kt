package com.paradigma.rickyandmorty.data.repository.local.favorites


import com.paradigma.rickyandmorty.domain.Character

interface FavoritesRepository {

    suspend fun getAllFavoriteCharacters(): ResultFavorites
    suspend fun saveCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
}