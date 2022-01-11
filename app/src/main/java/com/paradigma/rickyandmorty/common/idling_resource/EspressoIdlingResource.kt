package com.paradigma.rickyandmorty.common.idling_resource

import androidx.test.espresso.IdlingResource


object EspressoIdlingResource {

    private const val resource = "GLOBAL"
    private val countingIdlingResource = SimpleCountingIdlingResource(resource)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()

    fun getIdlingResource(): IdlingResource = countingIdlingResource


}