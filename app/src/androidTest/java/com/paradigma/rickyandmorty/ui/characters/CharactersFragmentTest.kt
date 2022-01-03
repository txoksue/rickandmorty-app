package com.paradigma.rickyandmorty.ui.characters

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.launchFragmentInHiltContainer
import com.paradigma.rickyandmorty.util.getCharactersData
import com.paradigma.rickyandmorty.util.loadCharactersData
import com.paradigma.rickyandmorty.util.setCharacterError
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.*
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
class CharactersFragmentTest {

    companion object{
        private fun swipeFromTopToBottom(): ViewAction? {
            return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER
            )
        }
    }

    @Inject
    lateinit var characterRepository: CharacterRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun charactersFragment_showData() {

        characterRepository.loadCharactersData()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.recycler_view_character_list)).check(matches(isDisplayed()))
    }


    @Test
    fun charactersFragment_noData() {

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.component_characters_no_result)).check(matches(isDisplayed()))

        onView(withText(R.string.characters_no_results)).check(matches(isDisplayed()))
    }


    @Test
    fun charactersFragment_Error() {

        characterRepository.setCharacterError(true)

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.component_characters_no_result)).check(matches(isDisplayed()))

        onView(withText(R.string.characters_error)).check(matches(isDisplayed()))
    }



    @Test
    fun characterFragment_swipeUp(){

        characterRepository.loadCharactersData()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.recycler_view_character_list)).perform(swipeFromTopToBottom())

        allOf(withId(R.id.recycler_view_character_list), hasSibling(withId(R.id.progressBar)))
    }


    @Test
    fun charactersFragment_navigateToCharacterDetail() {

        characterRepository.loadCharactersData()

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CharactersFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.recycler_view_character_list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(characterRepository.getCharactersData()[0].name)), click()
                )
            )

        verify(navController).navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                characterRepository.getCharactersData()[0]
            )
        )
    }



}