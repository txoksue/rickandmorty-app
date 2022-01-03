package com.paradigma.rickyandmorty.repository.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.paradigma.rickyandmorty.FakeRickyAndMortyApiService
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepositoryImpl
import com.paradigma.rickyandmorty.domain.Location
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
        (rickyAndMortyApiService as FakeRickyAndMortyApiService).setCharacterLocationData()
    }


    @Test
    fun locationRepositoryImpl_getLocation() = runBlocking {
        val result = locationRepository.getLocation("3")

        val location = (rickyAndMortyApiService as FakeRickyAndMortyApiService).getCharacterLocationData()?.let {locationDTO->
            locationMapper.mapToDomain(locationDTO)
        }

        assertThat(result is ResultLocation.Success)
        result as ResultLocation.Success
        assertThat(result.data).isEqualTo(location)
    }
}