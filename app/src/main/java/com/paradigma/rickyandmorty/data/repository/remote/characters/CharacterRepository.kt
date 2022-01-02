package com.paradigma.rickyandmorty.data.repository.remote.characters

import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.domain.Character

interface CharacterRepository {

    suspend fun getCharacters(page: Int): ResultCharacters

}
