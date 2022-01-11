package com.paradigma.rickyandmorty.mockwebserver


import com.paradigma.rickyandmorty.mockwebserver.LoaderData.load
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import com.paradigma.rickyandmorty.mockwebserver.ResponseConfig.config

class NoDataDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        val responseBody = load("characters_no_data.json")
        return MockResponse().setResponseCode(200).setBody(responseBody)
    }
}