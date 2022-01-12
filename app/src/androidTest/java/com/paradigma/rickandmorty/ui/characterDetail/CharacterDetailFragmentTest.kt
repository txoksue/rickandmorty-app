package com.paradigma.rickandmorty.ui.characterDetail

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickandmorty.BuildConfig
import com.paradigma.rickandmorty.R
import com.paradigma.rickandmorty.common.idling_resource.EspressoIdlingResource
import com.paradigma.rickandmorty.domain.Location
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.ui.character_detail.CharacterDetailFragment
import com.paradigma.rickandmorty.launchFragmentInHiltContainer
import com.paradigma.rickandmorty.mockwebserver.SuccessDispatcher
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharacterDetailFragmentTest {

    private lateinit var locationToShow: Location
    private lateinit var bundle: Bundle
    private val mockWebServer by lazy { MockWebServer() }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())

        mockWebServer.start(BuildConfig.TEST_PORT)

        locationToShow = Location(3, "Citadel of Ricks", "Space station", "unknown")

        bundle = Bundle().apply {
            putParcelable("character", Character( 1,"Rick Sanchez","https://rickandmortyapi.com/api/character/avatar/1.jpeg","Male","","Alive","3"))
        }
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }


    @Test
    fun characterDetailFragment_showCharacterLocation(){

        mockWebServer.dispatcher = SuccessDispatcher()

        launchFragmentInHiltContainer<CharacterDetailFragment>(bundle)

        onView(withId(R.id.image_view_photo)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.text_view_name), withText(locationToShow.name)))
        onView(allOf(withId(R.id.text_view_type), withText(locationToShow.type)))
        onView(allOf(withId(R.id.text_view_dimension), withText(locationToShow.dimension)))
        allOf(withId(R.id.text_view_name), hasSibling(withText(bundle.getParcelable<Character>("character")?.name)))
    }


    @Test
    fun characterDetailFragment_clickFavorite(){

        mockWebServer.dispatcher = SuccessDispatcher()

        launchFragmentInHiltContainer<CharacterDetailFragment>(bundle)

        onView(withId(R.id.image_view_favorite)).perform(click())
        onView(allOf(withId(R.id.image_view_favorite), hasSibling(withId(R.drawable.ic_favorite_star))))

        onView(withId(R.id.image_view_favorite)).perform(click())
        onView(allOf(withId(R.id.image_view_favorite), hasSibling(withId(R.drawable.ic_no_favorite_star))))
    }


}
