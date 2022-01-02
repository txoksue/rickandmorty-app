package com.paradigma.rickyandmorty.data.repository.remote.location

import com.paradigma.rickyandmorty.data.repository.ResultLocation

interface LocationRepository {

    suspend fun getLocation(id: String): ResultLocation
}