package com.paradigma.rickyandmorty.data.repository.remote


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiServiceImpl
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickyandmorty.domain.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CharacterRepositoryImplTest{


    lateinit var characterRepository: CharacterRepositoryImpl

    @Mock
    private lateinit var rickyAndMortyApi: RickyAndMortyApi

    private lateinit var rickyAndMortyApiService: RickyAndMortyApiService

    @Mock
    private lateinit var characterMapper: Mapper<CharacterDTO, Character>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp(){
        rickyAndMortyApiService = RickyAndMortyApiServiceImpl(rickyAndMortyApi)
        characterRepository = CharacterRepositoryImpl(rickyAndMortyApiService, characterMapper)
    }


    @Test
    fun characterRepositoryImpl_getCharacters_page() = runBlockingTest {

        whenever(rickyAndMortyApiService.getCharacters(1)).thenReturn(mock())

        characterRepository.getCharacters(1)

        verify(rickyAndMortyApiService, times(1)).getCharacters(1)
    }
}