package com.paradigma.rickyandmorty.ui.characterDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.paradigma.rickyandmorty.*
import com.paradigma.rickyandmorty.data.mapper.CharacterDataMapper
import com.paradigma.rickyandmorty.data.mapper.LocationDataMapper
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.ScreenState
import com.paradigma.rickyandmorty.ui.character_detail.CharacterDetailViewModel
import com.paradigma.rickyandmorty.ui.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    private lateinit var characterRepository: FakeRepository<List<Character>>
    private lateinit var locationRepository: FakeRepository<Location?>
    private lateinit var favoriteRepository: FakeRepository<List<Character>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        characterRepository = FakeCharacterRepository(CharacterDataMapper())
        favoriteRepository = FakeFavoritesRepository()
        locationRepository = FakeLocationRepository(LocationDataMapper())

        characterRepository.setData(null)
        favoriteRepository.setData(arrayListOf(characterRepository.getData()[0], characterRepository.getData()[1]))
        locationRepository.setData(null)

        characterDetailViewModel = CharacterDetailViewModel(locationRepository as FakeLocationRepository,
            favoriteRepository as FakeFavoritesRepository
        )
    }


    @Test
    fun getLocation_locationExists() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<Location?>> {}

        characterDetailViewModel.statusScreen.observeForever(observer)

        characterDetailViewModel.character = characterRepository.getData()[0]
        characterDetailViewModel.getCharacterLocation()

        val value: ScreenState<Location?> = characterDetailViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.Results)
        assertThat(value, not(nullValue()))
    }


    @Test
    fun getLocation_nullCharacter() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<ScreenState<Location?>> {}

        characterDetailViewModel.statusScreen.observeForever(observer)

        characterDetailViewModel.character = null
        characterDetailViewModel.getCharacterLocation()

        val value: ScreenState<Location?> = characterDetailViewModel.statusScreen.getOrAwaitValue()

        assertTrue(value is ScreenState.NoData)
    }

    @Test
    fun getCharacter_isFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        characterDetailViewModel.showFavorite.observeForever(observer)

        characterDetailViewModel.checkIsFavorite(characterRepository.getData()[0].id)

        val value = characterDetailViewModel.showFavorite.getOrAwaitValue()

        assertThat(value, `is`(true))
    }


    @Test
    fun getCharacter_setAsFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        characterDetailViewModel.showFavorite.observeForever(observer)

        val character = characterRepository.getData().find { character -> character.id == 1 }

        characterDetailViewModel.character = character

        characterDetailViewModel.saveCharacterAsFavorite()

        assertThat(characterDetailViewModel.showFavorite.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun getCharacter_unSetAsFavorite() = mainCoroutineRule.runBlockingTest {

        val observer = Observer<Boolean> {}

        characterDetailViewModel.showFavorite.observeForever(observer)

        val character = characterRepository.getData().find { character -> character.id == 1011334 }

        characterDetailViewModel.character = character

        characterDetailViewModel.removeCharacterAsFavorite()

        assertThat(characterDetailViewModel.showFavorite.getOrAwaitValue(), `is`(false))
    }

}