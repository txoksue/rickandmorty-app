package com.paradigma.rickyandmorty.data.mapper

import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.domain.Location
import javax.inject.Inject


class LocationDataMapper @Inject constructor() : Mapper<LocationDTO, Location> {

    override fun mapToDomain(input: LocationDTO): Location {
        return Location(
            input.id ?: 0,
            input.name ?: "",
            input.type?: "",
            input.dimension?: "",
        )
    }

    override fun mapFromDomain(input: Location): LocationDTO {
        return LocationDTO(
            id = input.id,
            name = input.name,
            type = input.type,
            dimension = input.dimension,
            null,
            null,
            null,
        )
    }
}
