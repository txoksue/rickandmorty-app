package com.paradigma.rickandmorty.data.respository.local

import androidx.test.filters.MediumTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickandmorty.data.repository.local.favorites.ResultFavorites
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class FavoriteRepositoryImplTest{

    private lateinit var charactersList: List<Character>
    private lateinit var character: Character

    @Inject
    lateinit var database: FavoritesDataBase

    @Inject
    lateinit var favoriteRepository: FavoritesRepository

    @Inject
    lateinit var favoriteMapper: Mapper<Favorite, Character>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
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
        character = charactersList[0]
    }

    @After
    fun closeDb() {
        database.close()
    }


    @Test
    fun favoriteRepositoryImpl_getAllFavoritesCharacters(): Unit = runBlocking {

        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[0]))

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertThat(result is ResultFavorites.Success)
    }

    @Test
    fun favoritesRepositoryImpl_saveCharacter(): Unit = runBlocking {

        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[0]))

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertThat(result is ResultFavorites.Success)
        result as ResultFavorites.Success

        val characterToCompare = result.data.find { it.id == character.id }
        assertThat(characterToCompare?.id, `is`(character.id))
        assertThat(characterToCompare?.name, `is`(character.name))
        assertThat(characterToCompare?.gender, `is`(character.gender))
        assertThat(characterToCompare?.status, `is`(character.status))
        assertThat(characterToCompare?.type, `is`(character.type))
    }

    @Test
    fun favoritesRepositoryImpl_deleteCharacter(): Unit = runBlocking {

        database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(charactersList[0]))
        database.favoritesDao().deleteFavorite(favoriteMapper.mapFromDomain(charactersList[0]))

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertThat(result is ResultFavorites.NoData)
    }

}