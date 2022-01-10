package com.paradigma.rickyandmorty.data.repository.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiServiceImpl
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepositoryImpl
import com.paradigma.rickyandmorty.domain.Location
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LocationRepositoryImplTest {

    lateinit var locationRepository: LocationRepositoryImpl

    @Mock
    private lateinit var rickyAndMortyApi: RickyAndMortyApi


    private lateinit var rickyAndMortyApiService: RickyAndMortyApiService

    @Mock
    private lateinit var locationMapper: Mapper<LocationDTO, Location>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        rickyAndMortyApiService = RickyAndMortyApiServiceImpl(rickyAndMortyApi)
        locationRepository = LocationRepositoryImpl(rickyAndMortyApiService, locationMapper)
    }


    @Test
    fun locationRepositoryImpl_getLocation() = runBlockingTest {

        whenever(rickyAndMortyApiService.getLocation("3")).thenReturn(mock())

        locationRepository.getLocation("3")

        verify(rickyAndMortyApiService, times(1)).getLocation("3")
    }
}