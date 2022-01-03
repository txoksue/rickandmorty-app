package com.paradigma.rickyandmorty.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import com.paradigma.rickyandmorty.FakeCharacterRepository
import com.paradigma.rickyandmorty.FakeFavoritesRepository
import com.paradigma.rickyandmorty.FakeRepository
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.getOrAwaitValue
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.CharacterDataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.w3c.dom.CharacterData


@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    private lateinit var favoritesViewModel: FavoritesViewModel

    private lateinit var characterRepository: FakeRepository<List<Character>>
    private lateinit var favoriteRepository: FakeRepository<List<Character>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        favoriteRepository = FakeFavoritesRepository()
        characterRepository = FakeCharacterRepository(CharacterDataMapper())

        characterRepository.setData(null)
        favoriteRepository.setData(arrayListOf(characterRepository.getData()[0], characterRepository.getData()[1]))

        favoritesViewModel = FavoritesViewModel(favoriteRepository as FakeFavoritesRepository)
    }

    @Test
    fun getFavorites_isNotEmpty() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        favoritesViewModel.statusScreen.observeForever(observer)

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
    }

    @Test
    fun getFavorites_NoData() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        favoritesViewModel.statusScreen.observeForever(observer)

        favoriteRepository.clearData()

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.NoData)
    }


    @Test
    fun getFavorites_Error() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        favoritesViewModel.statusScreen.observeForever(observer)

        favoriteRepository.setReturnError(true)

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Error)
    }

}