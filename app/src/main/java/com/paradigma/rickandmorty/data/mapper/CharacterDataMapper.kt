package com.paradigma.rickandmorty.data.mapper

import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickandmorty.domain.Character
import javax.inject.Inject


class CharacterDataMapper @Inject constructor() : Mapper<CharacterDTO, Character> {

    override fun mapToDomain(input: CharacterDTO): Character {
        return Character(
            input.id ?: 0,
            input.name ?: "",
            input.image?: "",
            input.gender?: "",
            input.type?: "",
            input.status?: "",
            input.characterLocation?.url?.split("/")?.last()?: ""
        )
    }

    override fun mapFromDomain(input: Character): CharacterDTO {
        return CharacterDTO(
            null,
            null,
            input.gender,
            input.id,
            input.image,
            null,
            input.name,
            null,
            null,
            input.status,
            input.type,
            null,
        )
    }
}
