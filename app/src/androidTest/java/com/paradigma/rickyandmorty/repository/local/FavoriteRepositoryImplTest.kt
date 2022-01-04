package com.paradigma.rickyandmorty.repository.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.ResultCharacters
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepositoryImpl
import com.paradigma.rickyandmorty.data.repository.local.favorites.ResultFavorites
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.util.LoaderCharactersData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertTrue
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

    private var charactersList: List<Character>? = null
    private var character: Character? = null


    @Inject
    lateinit var database: FavoritesDataBase

    @Inject
    lateinit var favoriteRepository: FavoritesRepository

    @Inject
    lateinit var characterMapper: Mapper<CharacterDTO, Character>


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        charactersList = LoaderCharactersData.load().results?.map { characterDto -> characterMapper.mapToDomain(characterDto) }
        character = charactersList?.get(0)
    }

    @After
    fun closeDb() = database.close()


    @Test
    fun favoriteRepositoryImpl_getAllFavoritesCharacters() = runBlocking {

        charactersList?.let {
            favoriteRepository.saveCharacter(it[0])
            favoriteRepository.saveCharacter(it[1])
        }

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertTrue(result is ResultFavorites.Success)
    }

    @Test
    fun favoritesRepositoryImpl_saveCharacter() = runBlocking {
        character?.let{
            favoriteRepository.saveCharacter(it)
        }

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertTrue(result is ResultFavorites.Success)
        result as ResultFavorites.Success

        val characterToCompare = result.data.find { it.id == character?.id }
        assertThat(characterToCompare?.id, `is`(character?.id))
        assertThat(characterToCompare?.name, `is`(character?.name))
        assertThat(characterToCompare?.gender, `is`(character?.gender))
        assertThat(characterToCompare?.status, `is`(character?.status))
        assertThat(characterToCompare?.type, `is`(character?.type))
    }

    @Test
    fun favoritesRepositoryImpl_deleteCharacter() = runBlocking {
        character?.let{
            favoriteRepository.saveCharacter(it)
            favoriteRepository.deleteCharacter(it)
        }

        val result = favoriteRepository.getAllFavoriteCharacters()

        assertTrue(result is ResultFavorites.NoData)
    }

}