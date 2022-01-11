package com.paradigma.rickyandmorty.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    private lateinit  var charactersList: List<Character>

    private lateinit var charactersViewModel: CharactersViewModel

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        charactersViewModel = CharactersViewModel(characterRepository)

        charactersList = listOf(
            Character(
                1,
                "Rick Sanchez",
                "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                "Male",
                "",
                "Alive",
                "3"
            ),
            Character(
                2,
                "Morty Smith",
                "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                "Male",
                "",
                "Alive",
                "3",
            ),
            Character(
                3,
                "Summer Smith",
                "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                "Female",
                "",
                "Alive",
                "20"
            )
        )
    }


    @Test
    fun getCharacters_isNotNullOrEmpty() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        whenever(characterRepository.getCharacters(1)).thenReturn(ResultCharacters.Success(charactersList, 42))

        charactersViewModel.getCharacters()

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
        assertThat(value, not(nullValue()))
        assertThat((value as ScreenState.Results).data, not(emptyList()))
        assertEquals((value).data, charactersList)
    }

    @Test
    fun getFavorites_NoData() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        whenever(characterRepository.getCharacters(any())).thenReturn(ResultCharacters.NoData)

        charactersViewModel.getCharacters()

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.NoData)
    }



    @Test
    fun getCharacters_Error() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        whenever(characterRepository.getCharacters(1)).thenReturn(ResultCharacters.Error(Exception("Error getting characters")))

        charactersViewModel.getCharacters()

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Error)
    }

}