package com.paradigma.rickandmorty.data.repository.remote


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.paradigma.rickandmorty.MainCoroutineRule
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.ResultCharacters
import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiServiceImpl
import com.paradigma.rickandmorty.data.repository.remote.api.model.*
import com.paradigma.rickandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickandmorty.domain.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
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
import retrofit2.Response


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryImplTest {


    lateinit var characterRepository: CharacterRepositoryImpl

    @Mock
    private lateinit var rickyAndMortyApiService: RickyAndMortyApiServiceImpl

    @Mock
    private lateinit var characterMapper: Mapper<CharacterDTO, Character>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        characterRepository = CharacterRepositoryImpl(rickyAndMortyApiService, characterMapper)
    }


    @Test
    fun characterRepositoryImpl_getCharacters_page(): Unit = runBlocking {

        whenever(rickyAndMortyApiService.getCharacters(1)).thenReturn(mock())

        characterRepository.getCharacters(1)

        verify(rickyAndMortyApiService, times(1)).getCharacters(1)
    }


    @Test
    fun locationRepositoryImpl_getCharacters_Success() = runBlocking {

        val characterResponse = CharacterResponse(
            InfoDTO(826, "any url", 42, null),
            listOf(CharacterDTO("any date", listOf(), "Male", 1, "https://rickandmortyapi.com/api/character/avatar/3.jpeg", CharacterLocationDTO("Citadel of Ricks", "any url"), "Rick Sanchez", OriginDTO("Earth (C-137)", "any url"), "Human", "Alive", "", "https://rickandmortyapi.com/api/character/avatar/1.jpeg"))
        )
        val character = Character(1, "Rick Sanchez","https://rickandmortyapi.com/api/character/avatar/1.jpeg","Male","","Alive","3")

        whenever(rickyAndMortyApiService.getCharacters(1)).thenReturn(Response.success(characterResponse))

        whenever(characterMapper.mapToDomain(characterResponse.results?.get(0)!!)).thenReturn(character)

        val result = characterRepository.getCharacters(1)

        assertTrue(result is ResultCharacters.Success)
    }

    @Test
    fun locationRepositoryImpl_getCharacters_NoData() = runBlocking {

        whenever(rickyAndMortyApiService.getCharacters(1)).thenReturn(Response.success(null))

        val result = characterRepository.getCharacters(1)

        assertTrue(result is ResultCharacters.NoData)
    }


    @Test
    fun locationRepositoryImpl_getCharacters_Error() = runBlocking {

        whenever(rickyAndMortyApiService.getCharacters(1)).thenReturn(Response.error(400, "{}".toResponseBody()))

        val result = characterRepository.getCharacters(1)

        assertTrue(result is ResultCharacters.Error)
    }
}