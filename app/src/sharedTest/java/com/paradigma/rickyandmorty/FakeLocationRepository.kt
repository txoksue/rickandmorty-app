package com.paradigma.rickyandmorty

import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.util.LoaderLocationData
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakeLocationRepository @Inject constructor(var locationMapper: Mapper<LocationDTO, Location>): LocationRepository, FakeRepository<Location?>{

    private var characterLocation: LocationDTO? = null
    private var shouldReturnError: Boolean = false


    override suspend fun getLocation(id: String): ResultLocation {
        if (shouldReturnError){
            return ResultLocation.Error(Exception("Error getting characters"))
        }

        return characterLocation?.let { locationDTO ->
                ResultLocation.Success(locationMapper.mapToDomain(locationDTO))
        } ?: ResultLocation.NoData
    }

    override fun setData(data: Location?) {
        runBlocking {
            characterLocation = LoaderLocationData.load()
        }
    }


    override fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }


    override fun clearData() {
        characterLocation = null
    }


    override fun getData() = characterLocation?.let{locationMapper.mapToDomain(it)}
}