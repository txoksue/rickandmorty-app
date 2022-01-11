package com.paradigma.rickyandmorty.data.respository.local.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickyandmorty.domain.Character
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoritesDataBaseTest {

    private var character: Character? = null

    @Inject
    lateinit var database: FavoritesDataBase

    @Inject
    lateinit var favoriteMapper: Mapper<Favorite, Character>


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        character = Character(1, "Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Male","","Alive","3")
    }

    @After
    fun cleanUp() {
        database.close()
    }


    @Test
    fun favoritesDataBase_getFavorites() = runBlockingTest {

        character?.let {character ->
            database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(character))
        }

        val result = database.favoritesDao().getFavorites()

        assertThat(result, notNullValue())
    }


    @Test
    fun favoritesDataBase_insertFavorite() = runBlockingTest {

        character?.let {character ->
            database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(character))
        }

        val result = database.favoritesDao().getFavorites()

        assertThat(result, notNullValue())
        assertThat(result[0].id, `is`(character?.id))
        assertThat(result[0].name, `is`(character?.name))
        assertThat(result[0].type, `is`(character?.type))
    }


    @Test
    fun favoritesDataBase_deleteFavorite() = runBlockingTest {

        character?.let {character ->
            database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(character))
        }
        character?.let {character ->
            database.favoritesDao().deleteFavorite(favoriteMapper.mapFromDomain(character))
        }

        val result = database.favoritesDao().getFavorites()

        assertThat(result.isEmpty(), `is`(true))
    }

}