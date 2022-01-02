package com.paradigma.rickyandmorty.data.repository

import com.paradigma.rickyandmorty.domain.Location

sealed class ResultLocation {

    data class Success(val data: Location) : ResultLocation()
    data class Error(val exception: Exception) : ResultLocation()
    object NoData: ResultLocation()
}