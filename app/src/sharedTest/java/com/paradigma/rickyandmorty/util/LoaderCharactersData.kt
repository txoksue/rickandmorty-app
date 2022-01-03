package com.paradigma.rickyandmorty.util

import com.google.gson.Gson
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterResponse
import java.io.InputStreamReader
import java.io.Reader


class LoaderCharactersData {

    companion object {

        fun load(): CharacterResponse {
            val inputStream = javaClass.classLoader?.getResourceAsStream("characters_data.json")
            val reader: Reader = InputStreamReader(inputStream, "UTF-8")
            return Gson().fromJson(reader, CharacterResponse::class.java)
        }
    }
}
