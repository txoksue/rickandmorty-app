package com.paradigma.rickyandmorty.mockwebserver

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.paradigma.rickyandmorty.mockwebserver.LoaderData.load
import com.paradigma.rickyandmorty.mockwebserver.ResponseConfig.config
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest


const val GET_CHARACTERS = "character"
const val GET_LOCATION = "location"
const val CHARACTERS_DATA_SUCCESS = "characters_data.json"
const val CHARACTER_LOCATION_DATA_SUCCESS = "location_data.json"

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
) : Dispatcher() {

    private val responseFilesByPath: Map<String, String> = mapOf(
        GET_CHARACTERS to CHARACTERS_DATA_SUCCESS,
        GET_LOCATION to CHARACTER_LOCATION_DATA_SUCCESS
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutParams = Uri.parse(request.path).pathSegments[0] ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutParams]

        return if (responseFile != null) {
            val responseBody = load(responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            throw Throwable("Uri.parse(request.path).path null")
            errorResponse
        }
    }
}