package com.paradigma.rickandmorty.ui.favorites


import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickandmorty.R
import com.paradigma.rickandmorty.common.idling_resource.EspressoIdlingResource
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
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

    private lateinit var charactersList: List<Character>

    @Inject
    lateinit var database: FavoritesDataBase

    @Inject
    lateinit var favoriteMapper: Mapper<Favorite, Character>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource())
        charactersList = listOf(
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
    }

    @After
    fun cleanUp() {
        database.close()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource())
    }



    @Test
    fun favoritesFragment_showData() {

        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[0]))
        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[1]))
        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[2]))

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
    fun favoritesFragment_navigateToCharacterDetail() {

        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[0]))
        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[1]))
        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[2]))

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<FavoritesFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.recycler_view_favorite_list))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(charactersList[1].name)), click()
                )
            )

        verify(navController).navigate(
            FavoritesFragmentDirections.actionFavoritesFragmentToCharacterDetailFragment(
                charactersList[1]
            )
        )
    }


}

