package com.paradigma.rickandmorty.ui.characters

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.GeneralSwipeAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Swipe
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickandmorty.BuildConfig
import com.paradigma.rickandmorty.R
import com.paradigma.rickandmorty.common.idling_resource.EspressoIdlingResource
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.launchFragmentInHiltContainer
import com.paradigma.rickandmorty.mockwebserver.ErrorDispatcher
import com.paradigma.rickandmorty.mockwebserver.NoDataDispatcher
import com.paradigma.rickandmorty.mockwebserver.SuccessDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharactersFragmentTest {

    private val mockWebServer by lazy { MockWebServer() }

    companion object {
        private fun swipeFromTopToBottom(): ViewAction? {
            return GeneralSwipeAction(
                Swipe.FAST, GeneralLocation.BOTTOM_CENTER,
                GeneralLocation.TOP_CENTER, Press.FINGER
            )
        }
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer.start(BuildConfig.TEST_PORT)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }

    @Test
    fun charactersFragment_ShowData() {

        mockWebServer.dispatcher = SuccessDispatcher()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.recycler_view_character_list)).check(matches(isDisplayed()))
    }


    @Test
    fun characterFragment_SwipeUp() {

        mockWebServer.dispatcher = SuccessDispatcher()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.recycler_view_character_list)).perform(swipeFromTopToBottom())

        allOf(withId(R.id.recycler_view_character_list), hasSibling(withId(R.id.progressBar)))
    }


    @Test
    fun charactersFragment_NoData() {

        mockWebServer.dispatcher = NoDataDispatcher()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.component_characters_no_result)).check(matches((isDisplayed())))

        onView(withText(R.string.characters_no_results)).check(matches((isDisplayed())))
    }


    @ExperimentalCoroutinesApi
    @Test
    fun charactersFragment_Error() {

        mockWebServer.dispatcher = ErrorDispatcher()

        launchFragmentInHiltContainer<CharactersFragment>()

        onView(withId(R.id.component_characters_no_result)).check(matches(isDisplayed()))

        onView(withText(R.string.characters_error)).check(matches(isDisplayed()))

    }


    @Test
    fun charactersFragment_navigateToCharacterDetail() {

        mockWebServer.dispatcher = SuccessDispatcher()

        val character = Character(1, "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Male", "", "Alive", "3")

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<CharactersFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }


        onView(allOf(withId(R.id.recycler_view_character_list), isDisplayed()))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(character.name)), click()
                )
            )


        verify(navController).navigate(
            CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(character)
        )
    }

}