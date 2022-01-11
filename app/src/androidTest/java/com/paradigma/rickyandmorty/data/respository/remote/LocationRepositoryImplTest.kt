package com.paradigma.rickyandmorty.data.respository.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.paradigma.rickyandmorty.BuildConfig
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepositoryImpl
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.mockwebserver.ErrorDispatcher
import com.paradigma.rickyandmorty.mockwebserver.NoDataDispatcher
import com.paradigma.rickyandmorty.mockwebserver.SuccessDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LocationRepositoryImplTest{

    private val mockWebServer by lazy { MockWebServer() }

    @Inject
    lateinit var locationRepository: LocationRepositoryImpl

    @Inject
    lateinit var rickyAndMortyApiService: RickyAndMortyApiService

    @Inject
    lateinit var locationMapper: Mapper<LocationDTO, Location>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp(){
        hiltRule.inject()
        mockWebServer.start(BuildConfig.TEST_PORT)
    }

    @After
    fun cleanUp() {
        mockWebServer.shutdown()
    }

    @Test
    fun locationRepositoryImpl_getLocation(): Unit = runBlocking {
        mockWebServer.dispatcher = SuccessDispatcher()

        val result = locationRepository.getLocation("3")

        assertThat(result is ResultLocation.Success)
    }

    @Test
    fun locationRepositoryImpl_getLocation_NoData(): Unit = runBlocking {
        mockWebServer.dispatcher = NoDataDispatcher()

        val result = locationRepository.getLocation("3")

        assertThat(result is ResultLocation.NoData)
    }

    @Test
    fun locationRepositoryImpl_getLocation_Error(): Unit = runBlocking {
        mockWebServer.dispatcher = ErrorDispatcher()

        val result = locationRepository.getLocation("3")

        assertThat(result is ResultLocation.Error)
    }


}