package com.paradigma.rickandmorty.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paradigma.rickandmorty.ui.ScreenState
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.ui.getOrAwaitValue
import com.paradigma.rickandmorty.MainCoroutineRule
import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickandmorty.data.repository.local.favorites.ResultFavorites
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoritesViewModelTest {

    private lateinit var favoritesViewModel: FavoritesViewModel

    @Mock
    private lateinit var favoriteRepository: FavoritesRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        favoritesViewModel = FavoritesViewModel(favoriteRepository)
    }

    @Test
    fun getFavorites_isNotEmpty() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        val charactersList = listOf(
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

        favoritesViewModel.statusScreen.observeForever(observer)

        whenever(favoriteRepository.getAllFavoriteCharacters()).thenReturn(ResultFavorites.Success(charactersList))

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
    }

    @Test
    fun getFavorites_NoData() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        favoritesViewModel.statusScreen.observeForever(observer)

        whenever(favoriteRepository.getAllFavoriteCharacters()).thenReturn(ResultFavorites.NoData)

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.NoData)
    }


    @Test
    fun getFavorites_Error() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<List<Character?>>> {}

        favoritesViewModel.statusScreen.observeForever(observer)

        whenever(favoriteRepository.getAllFavoriteCharacters()).thenReturn(ResultFavorites.Error(Exception("Error getting favorites")))

        favoritesViewModel.getFavorites()

        val value: ScreenState<List<Character?>> = favoritesViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Error)
    }

}