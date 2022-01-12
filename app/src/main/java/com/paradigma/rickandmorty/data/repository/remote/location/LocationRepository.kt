package com.paradigma.rickandmorty.data.repository.remote.location

import com.paradigma.rickandmorty.data.repository.ResultLocation

interface LocationRepository {

    suspend fun getLocation(id: String?): ResultLocation
}