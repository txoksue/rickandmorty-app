package com.paradigma.rickyandmorty.mockwebserver

import android.content.Context
import java.io.InputStream
import java.io.InputStreamReader


object LoaderData {

    fun load(context: Context, json: String): String {
        val inputStream = javaClass.classLoader.getResourceAsStream(json)
        return inputStreamToString(inputStream, "UTF-8")
    }


    private fun inputStreamToString(inputStream: InputStream, charsetName: String): String {
        val builder = StringBuilder()
        val reader = InputStreamReader(inputStream, charsetName)
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}
