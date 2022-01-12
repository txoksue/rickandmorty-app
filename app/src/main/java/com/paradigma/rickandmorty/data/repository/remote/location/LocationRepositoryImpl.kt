package com.paradigma.rickandmorty.data.repository.remote.location

import android.util.Log
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.ResultLocation
import com.paradigma.rickandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickandmorty.domain.Location
import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(var rickyAndMortyApiService: RickyAndMortyApiService, var locationMapper: Mapper<LocationDTO, Location>) : LocationRepository {

    companion object {
        val TAG: String = LocationRepositoryImpl::class.java.name
    }

    override suspend fun getLocation(id: String?): ResultLocation = withContext(Dispatchers.IO) {

        try {

            id?.let { val response = rickyAndMortyApiService.getLocation(id)

                if (response.isSuccessful) {

                    return@withContext response.body()?.let { locationDTO ->
                        ResultLocation.Success(locationMapper.mapToDomain(locationDTO))
                    } ?: ResultLocation.NoData
                }

                return@withContext ResultLocation.Error(IOException("Error getting location $id - ${response.code()} ${response.message()}"))

            }?: ResultLocation.NoData

        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
            return@withContext ResultLocation.Error(IOException("Error getting location", e))
        }

    }

}