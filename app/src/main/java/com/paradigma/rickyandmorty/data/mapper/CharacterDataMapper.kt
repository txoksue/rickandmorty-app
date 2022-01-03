package com.paradigma.rickyandmorty.data.mapper

import android.content.Context
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.domain.Character
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class CharacterDataMapper @Inject constructor(@ApplicationContext val appContext: Context) : Mapper<CharacterDTO, Character> {

    override fun mapToDomain(input: CharacterDTO): Character {
        return Character(
            input.id?: -1,
            input.name ?: appContext.getString(R.string.no_name),
            input.image?: "",
            input.gender?: appContext.getString(R.string.no_gender),
            input.type?: appContext.getString(R.string.no_type),
            input.status?: appContext.getString(R.string.no_status),
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
