package com.paradigma.rickyandmorty.data.repository.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Dao
import com.paradigma.rickyandmorty.MainCoroutineRule
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDao
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepositoryImpl
import com.paradigma.rickyandmorty.domain.Character
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteRepositoryImplTest {

    private lateinit var favoriteRepository: FavoritesRepositoryImpl

    @Mock
    private lateinit var database: FavoritesDataBase

    @Mock
    private lateinit var favoriteMapper: Mapper<Favorite, Character>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        favoriteRepository = FavoritesRepositoryImpl(database, favoriteMapper)
    }


    @Test
    fun favoriteRepositoryImpl_getAllFavoritesCharacters() = runBlockingTest {

        whenever(database.favoritesDao().getFavorites()).thenReturn(mock())

        favoriteRepository.getAllFavoriteCharacters()

        verify(database, times(1)).favoritesDao().getFavorites()
    }

    @Test
    fun favoritesRepositoryImpl_saveCharacter() = runBlockingTest {

        whenever(database.favoritesDao().insertFavorite(mock())).thenReturn(mock())

        favoriteRepository.saveCharacter(mock())

        verify(database.favoritesDao(), times(1)).insertFavorite(mock())
    }

    @Test
    fun favoritesRepositoryImpl_deleteCharacter() = runBlockingTest {

        whenever(database.favoritesDao().deleteFavorite(mock())).thenReturn(mock())

        favoriteRepository.deleteCharacter(mock())

        verify(database.favoritesDao(), times(1)).deleteFavorite(mock())
    }

}