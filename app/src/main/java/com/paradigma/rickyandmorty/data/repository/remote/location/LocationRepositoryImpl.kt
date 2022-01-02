package com.paradigma.rickyandmorty.data.repository.remote.location

import android.util.Log
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterLocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.domain.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(var rickyAndMortyApi: RickyAndMortyApi) : LocationRepository {

    @Inject
    lateinit var locationMapper: Mapper<LocationDTO, Location>

    companion object {
        val TAG: String = LocationRepositoryImpl::class.java.name
    }

    override suspend fun getLocation(id: String): ResultLocation = withContext(Dispatchers.IO) {

        try {

            val response = rickyAndMortyApi.getLocationById(id)

            if (response.isSuccessful) {

                return@withContext response.body()?.let { locationDTO ->
                    ResultLocation.Success(locationMapper.mapToDomain(locationDTO))
                } ?: ResultLocation.NoData
            }

            return@withContext ResultLocation.Error(IOException("Error getting location $id - ${response.code()} ${response.message()}"))

        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
            return@withContext ResultLocation.Error(IOException("Error getting location", e))
        }

    }

}