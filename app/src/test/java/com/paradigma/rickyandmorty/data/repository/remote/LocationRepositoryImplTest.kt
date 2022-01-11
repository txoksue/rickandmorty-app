package com.paradigma.rickyandmorty.data.repository.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiServiceImpl
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepositoryImpl
import com.paradigma.rickyandmorty.domain.Location
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocationRepositoryImplTest {

    lateinit var locationRepository: LocationRepositoryImpl

    @Mock
    private lateinit var rickyAndMortyApiService: RickyAndMortyApiServiceImpl

    @Mock
    private lateinit var locationMapper: Mapper<LocationDTO, Location>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        locationRepository = LocationRepositoryImpl(rickyAndMortyApiService, locationMapper)
    }


    @Test
    fun locationRepositoryImpl_getLocation(): Unit = runBlocking {

        whenever(rickyAndMortyApiService.getLocation("3")).thenReturn(mock())

        locationRepository.getLocation("3")

        verify(rickyAndMortyApiService, times(1)).getLocation("3")
    }

    @Test
    fun locationRepositoryImpl_getLocation_Success() = runBlocking {

        val locationDTO = LocationDTO(3, "Citadel of Ricks", "Space station", "unknown", arrayListOf(), "any url", "any date")
        val location = Location(3, "Citadel of Ricks", "Space station", "unknown")

        whenever(rickyAndMortyApiService.getLocation("3")).thenReturn(Response.success(locationDTO))

        whenever(locationMapper.mapToDomain(locationDTO)).thenReturn(location)

        val result = locationRepository.getLocation("3")

        assertTrue(result is ResultLocation.Success)
    }

    @Test
    fun locationRepositoryImpl_getLocation_NoData() = runBlocking {

        whenever(rickyAndMortyApiService.getLocation("3")).thenReturn(Response.success(null))

        val result = locationRepository.getLocation("3")

        assertTrue(result is ResultLocation.NoData)
    }


    @Test
    fun locationRepositoryImpl_getLocation_Error() = runBlocking {

        whenever(rickyAndMortyApiService.getLocation("3")).thenReturn(Response.error(400, "{}".toResponseBody()))

        val result = locationRepository.getLocation("3")

        assertTrue(result is ResultLocation.Error)
    }
}

