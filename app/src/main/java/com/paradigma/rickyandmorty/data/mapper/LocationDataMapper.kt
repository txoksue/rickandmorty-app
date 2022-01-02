package com.paradigma.rickyandmorty.data.mapper

import android.content.Context
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.domain.Location
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class LocationDataMapper @Inject constructor(@ApplicationContext val appContext: Context) : Mapper<LocationDTO, Location> {

    override fun mapToDomain(input: LocationDTO): Location {
        return Location(
            input.id ?: 0,
            input.name ?: appContext.getString(R.string.no_name),
            input.type?: appContext.getString(R.string.no_type_available),
            input.dimension?: appContext.getString(R.string.no_dimension_available),
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
