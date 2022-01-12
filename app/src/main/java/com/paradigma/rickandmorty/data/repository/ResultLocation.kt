package com.paradigma.rickandmorty.data.repository

import com.paradigma.rickandmorty.domain.Location

sealed class ResultLocation {

    data class Success(val data: Location) : ResultLocation()
    data class Error(val exception: Exception) : ResultLocation()
    object NoData: ResultLocation()
}