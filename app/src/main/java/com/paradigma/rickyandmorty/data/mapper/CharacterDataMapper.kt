package com.paradigma.rickyandmorty.data.mapper

import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.domain.Location
import javax.inject.Inject


class CharacterDataMapper @Inject constructor() : Mapper<CharacterDTO, Character> {

    override fun mapToDomain(input: CharacterDTO): Character {
        return Character(
            input.id?: -1,
            input.name?: "",
            input.image?: "",
            input.gender?: "",
            Location("", ""),
            input.url?: ""
        )
    }

    override fun mapFromDomain(input: Character): CharacterDTO {
        return CharacterDTO(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        )
    }
}
