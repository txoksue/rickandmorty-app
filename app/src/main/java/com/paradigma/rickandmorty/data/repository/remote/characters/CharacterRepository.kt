package com.paradigma.rickandmorty.data.repository.remote.characters

import com.paradigma.rickandmorty.data.repository.ResultCharacters

interface CharacterRepository {

    suspend fun getCharacters(page: Int): ResultCharacters

}
