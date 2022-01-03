package com.paradigma.rickyandmorty.data.repository.local.favorites

import com.paradigma.rickyandmorty.domain.Character

sealed class ResultFavorites {

    data class Success(val data: List<Character>) : ResultFavorites()
    data class Error(val exception: Exception) : ResultFavorites()
    object NoData: ResultFavorites()
}