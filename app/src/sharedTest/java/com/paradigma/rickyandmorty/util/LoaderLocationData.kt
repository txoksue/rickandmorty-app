package com.paradigma.rickyandmorty.util

import com.google.gson.Gson
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import java.io.InputStreamReader
import java.io.Reader


class LoaderLocationData {

    companion object {

        fun load(): LocationDTO {
            val inputStream = javaClass.classLoader?.getResourceAsStream("location_data.json")
            val reader: Reader = InputStreamReader(inputStream, "UTF-8")
            return Gson().fromJson(reader, LocationDTO::class.java)
        }
    }
}
