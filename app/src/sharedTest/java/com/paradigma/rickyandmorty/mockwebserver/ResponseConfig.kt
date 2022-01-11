package com.paradigma.rickyandmorty.mockwebserver

import okhttp3.mockwebserver.MockResponse
import java.util.concurrent.TimeUnit

object ResponseConfig {

    fun config(mockResponse: MockResponse) = mockResponse
        .addHeader("Content-Type", "application/json; charset=utf-8")
        .addHeader("Cache-Control", "no-cache")
        .addHeader("Accept-Encoding", "identity")
        .addHeader("Connection", "close")
        .setBodyDelay(3, TimeUnit.SECONDS)
}