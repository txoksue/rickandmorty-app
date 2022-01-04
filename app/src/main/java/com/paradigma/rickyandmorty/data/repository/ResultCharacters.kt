package com.paradigma.rickyandmorty.data.repository

import com.paradigma.rickyandmorty.domain.Character

sealed class ResultCharacters {

    data class Success(val data: List<Character>, val totalPages: Int) : ResultCharacters()
    data class Error(val exception: Exception) : ResultCharacters()
    object NoData: ResultCharacters()
}