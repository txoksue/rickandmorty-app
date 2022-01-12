package com.paradigma.rickandmorty.data.respository.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickandmorty.BuildConfig
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.ResultCharacters
import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.mockwebserver.ErrorDispatcher
import com.paradigma.rickandmorty.mockwebserver.NoDataDispatcher
import com.paradigma.rickandmorty.mockwebserver.SuccessDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.google.common.truth.Truth.assertThat
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
class CharacterRepositoryImplTest{

    private val mockWebServer by lazy { MockWebServer() }

    @Inject
    lateinit var characterRepository: CharacterRepositoryImpl

    @Inject
    lateinit var rickyAndMortyApiService: RickyAndMortyApiService

    @Inject
    lateinit var characterMapper: Mapper<CharacterDTO, Character>

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
    fun characterRepositoryImpl_getCharacters_page(): Unit = runBlocking {
        mockWebServer.dispatcher = SuccessDispatcher()
        
        val result = characterRepository.getCharacters(1)

        assertThat(result is ResultCharacters.Success)
    }

    @Test
    fun characterRepositoryImpl_getCharacters_NoData(): Unit = runBlocking {
        mockWebServer.dispatcher = NoDataDispatcher()

        val result = characterRepository.getCharacters(1)

        assertThat(result is ResultCharacters.NoData)
    }

    @Test
    fun characterRepositoryImpl_getCharacters_Error(): Unit = runBlocking {
        mockWebServer.dispatcher = ErrorDispatcher()

        val result = characterRepository.getCharacters(1)

        assertThat(result is ResultCharacters.Error)
    }
}