package com.paradigma.rickandmorty.mockwebserver


import com.paradigma.rickandmorty.mockwebserver.LoaderData.load
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class NoDataDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = load("characters_no_data.json")
        return MockResponse().setResponseCode(200).setBody(responseBody)
    }
}