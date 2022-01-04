package com.paradigma.rickyandmorty.ui.characterDetail

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickyandmorty.R
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.domain.Location
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.ui.character_detail.CharacterDetailFragment
import com.paradigma.rickyandmorty.launchFragmentInHiltContainer
import com.paradigma.rickyandmorty.util.getCharactersData
import com.paradigma.rickyandmorty.util.getLocationData
import com.paradigma.rickyandmorty.util.loadCharactersData
import com.paradigma.rickyandmorty.util.loadLocationData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharacterDetailFragmentTest {

    private lateinit var locationToShow: Location
    private lateinit var character: Character
    private val bundle: Bundle = Bundle()

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    @Inject
    lateinit var characterRepository: CharacterRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        characterRepository.loadCharactersData()
        character = characterRepository.getCharactersData()[0]
        locationRepository.loadLocationData()
        locationRepository.getLocationData()?.let { locationToShow = it }
        bundle.putParcelable("character", character)
    }


    @Test
    fun characterDetailFragment_showCharacterLocation(){

        launchFragmentInHiltContainer<CharacterDetailFragment>(bundle)

        onView(withId(R.id.image_view_photo)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.text_view_name), withText(locationToShow.name)))
        onView(allOf(withId(R.id.text_view_type), withText(locationToShow.type)))
        onView(allOf(withId(R.id.text_view_dimension), withText(locationToShow.dimension)))
        allOf(withId(R.id.text_view_name), hasSibling(withText(bundle.getParcelable<Character>("character")?.name)))
    }


    @Test
    fun characterDetailFragment_clickFavorite(){

        launchFragmentInHiltContainer<CharacterDetailFragment>(bundle)

        onView(withId(R.id.image_view_favorite)).perform(click())
        onView(allOf(withId(R.id.image_view_favorite), hasSibling(withId(R.drawable.ic_favorite_star))))

        onView(withId(R.id.image_view_favorite)).perform(click())
        onView(allOf(withId(R.id.image_view_favorite), hasSibling(withId(R.drawable.ic_no_favorite_star))))
    }


}
