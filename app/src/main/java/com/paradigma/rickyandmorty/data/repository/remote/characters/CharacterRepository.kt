package com.paradigma.rickyandmorty.data.repository.remote.characters

import com.paradigma.rickyandmorty.data.repository.ResultCharacters

interface CharacterRepository {

    suspend fun getCharacters(page: Int): ResultCharacters

}
