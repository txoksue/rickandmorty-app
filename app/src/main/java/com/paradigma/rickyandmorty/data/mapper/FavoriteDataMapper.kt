package com.paradigma.rickyandmorty.data.mapper

import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickyandmorty.domain.Character
import javax.inject.Inject

class FavoriteDataMapper @Inject constructor() : Mapper<Favorite, Character> {

    override fun mapToDomain(input: Favorite): Character {
        return Character(
            id = input.id,
            name = input.name,
            image = input.image,
            gender = input.gender,
            status = input.status,
            type = input.type,
            locationId = input.locationId,
        )
    }

    override fun mapFromDomain(input: Character): Favorite {
        return Favorite(
            id = input.id,
            name = input.name,
            image = input.image,
            gender = input.gender,
            type = input.type,
            status = input.status,
            locationId = input.locationId
        )
    }
}