package com.paradigma.rickyandmorty.ui.characterDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paradigma.rickyandmorty.*
import com.paradigma.rickyandmorty.data.repository.ResultLocation
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.ResultFavorites
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.ui.character_detail.CharacterDetailViewModel
import com.paradigma.rickyandmorty.ui.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
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
class CharacterDetailViewModelTest {

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Mock
    private lateinit var locationRepository: LocationRepository

    @Mock
    private lateinit var favoriteRepository: FavoritesRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        characterDetailViewModel = CharacterDetailViewModel(locationRepository, favoriteRepository)
    }


    @Test
    fun getLocation_locationExists() = mainCoroutineRule.runBlockingTest {

        val location = Location(3, "Citadel of Ricks", "Space station", "unknown")
        val character = Character(
            1,
            "Rick Sanchez",
            "Any Url",
            "Male",
            "",
            "Alive",
            "3"
        )

        val observer = Observer<ScreenState<Location?>> {}

        characterDetailViewModel.statusScreen.observeForever(observer)

        whenever(locationRepository.getLocation(character.locationId)).thenReturn(ResultLocation.Success(location))

        characterDetailViewModel.character = character
        characterDetailViewModel.getCharacterLocation()

        val value: ScreenState<Location?> = characterDetailViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
        assertThat(value, not(nullValue()))
        value as ScreenState.Results
        assertEquals(location, value.data)
    }


    @Test
    fun getLocation_nullCharacter() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<Location>> {}

        characterDetailViewModel.statusScreen.observeForever(observer)

        characterDetailViewModel.character = null
        characterDetailViewModel.getCharacterLocation()

        val value: ScreenState<Location> = characterDetailViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.NoData)
    }

    @Test
    fun getCharacter_isFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        val charactersList = listOf(
            Character(
                1,
                "Rick Sanchez",
                "Any Url",
                "Male",
                "",
                "Alive",
                "3"
            ),
            Character(
                2,
                "Morty Smith",
                "Any Url",
                "Male",
                "",
                "Alive",
                "3",
            ),
            Character(
                3,
                "Summer Smith",
                "Any Url",
                "Female",
                "",
                "Alive",
                "20"
            )
        )

        characterDetailViewModel.showFavorite.observeForever(observer)

        whenever(favoriteRepository.getAllFavoriteCharacters()).thenReturn(ResultFavorites.Success(charactersList))

        characterDetailViewModel.checkIsFavorite(charactersList[0].id)

        val value = characterDetailViewModel.showFavorite.getOrAwaitValue()

        assertThat(value, `is`(true))
    }


    @Test
    fun getCharacter_setAsFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        characterDetailViewModel.showFavorite.observeForever(observer)

        val character = Character(
                1,
                "Rick Sanchez",
                "Any Url",
                "Male",
                "",
                "Alive",
                "3"
            )

        characterDetailViewModel.character = character

        characterDetailViewModel.saveCharacterAsFavorite()

        assertThat(characterDetailViewModel.showFavorite.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun getCharacter_unSetAsFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        characterDetailViewModel.showFavorite.observeForever(observer)

        val character = Character(
            1,
            "Rick Sanchez",
            "Any Url",
            "Male",
            "",
            "Alive",
            "3"
        )

        characterDetailViewModel.character = character

        characterDetailViewModel.removeCharacterAsFavorite()

        assertThat(characterDetailViewModel.showFavorite.getOrAwaitValue(), `is`(false))
    }

}