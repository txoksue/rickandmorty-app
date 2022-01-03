package com.paradigma.rickyandmorty.ui.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paradigma.rickyandmorty.FakeCharacterRepository
import com.paradigma.rickyandmorty.FakeRepository
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.CharacterDataMapper
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    private lateinit var charactersViewModel: CharactersViewModel

    private lateinit var characterRepository: FakeRepository<List<Character>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        characterRepository = FakeCharacterRepository(CharacterDataMapper())
        characterRepository.setData(null)
        charactersViewModel = CharactersViewModel(characterRepository as FakeCharacterRepository)
    }


    @Test
    fun getCharacters_isNotNull() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        charactersViewModel

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
        assertThat(value, not(nullValue()))
    }


    @Test
    fun getCharacters_isNotEmpty() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        charactersViewModel.getCharacters()

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
        assertThat((value as ScreenState.Results).data, not(emptyList()))
    }

    @Test
    fun getCharacters_Error() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        charactersViewModel.statusScreen.observeForever(observer)

        characterRepository.setReturnError(true)

        charactersViewModel.getCharacters()

        val value: ScreenState<List<Character?>> = charactersViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Error)
    }

}