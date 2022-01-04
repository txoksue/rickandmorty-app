package com.paradigma.rickyandmorty.ui.favorites

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.launchFragmentInHiltContainer
import com.paradigma.rickyandmorty.util.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    @Inject
    lateinit var characterRepository: CharacterRepository

    @Inject
    lateinit var favoriteRepository: FavoritesRepository


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun favoritesFragment_showData() {

        characterRepository.loadCharactersData()
        favoriteRepository.setFavorites(arrayListOf(characterRepository.getCharactersData()[0], characterRepository.getCharactersData()[1]))

        launchFragmentInHiltContainer<FavoritesFragment>()

        onView(withId(R.id.recycler_view_favorite_list)).check(matches(isDisplayed()))
    }


    @Test
    fun favoritesFragment_noData() {

        launchFragmentInHiltContainer<FavoritesFragment>()

        onView(withId(R.id.component_favorite_no_result)).check(matches(isDisplayed()))

        onView(withText(R.string.favorites_no_results)).check(matches(isDisplayed()))
    }


    @Test
    fun favoritesFragment_Error() {

        favoriteRepository.setFavoritesError(true)

        launchFragmentInHiltContainer<FavoritesFragment>()

        onView(withId(R.id.component_favorite_no_result)).check(matches(isDisplayed()))

        onView(withText(R.string.characters_error)).check(matches(isDisplayed()))
    }


    @Test
    fun favoritesFragment_navigateToCharacterDetail() {

        characterRepository.loadCharactersData()
        favoriteRepository.setFavorites(arrayListOf(characterRepository.getCharactersData()[0], characterRepository.getCharactersData()[1]))

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<FavoritesFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.recycler_view_favorite_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(favoriteRepository.getFavoritesData()[0].name)), click()
                )
            )

        verify(navController).navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToCharacterDetailFragment(
                favoriteRepository.getFavoritesData()[0]
            )
        )
    }


}

