package com.paradigma.rickyandmorty.data.repository.local.favorites

import android.util.Log
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDataBase
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(var database: FavoritesDataBase, var favoriteMapper: Mapper<Favorite, Character>) : FavoritesRepository {

    companion object {
        val TAG: String = FavoritesRepositoryImpl::class.java.name
    }


    override suspend fun getAllFavoriteCharacters(): ResultFavorites = withContext(Dispatchers.IO) {

        try {

            val favorites = database.favoritesDao().getFavorites()
            val favoritesList = favorites.map { entity ->
                 favoriteMapper.mapToDomain(entity)
            }

            return@withContext if (favoritesList.isNotEmpty()) ResultFavorites.Success(favoritesList) else ResultFavorites.NoData

        } catch (ex: Exception) {
            Log.e(TAG, ex.printStackTrace().toString())
            return@withContext ResultFavorites.Error(ex)
        }
    }


    override suspend fun saveCharacter(character: Character) {

        withContext(Dispatchers.IO) {

            try {

                database.favoritesDao().insertFavorite(favoriteMapper.mapFromDomain(character))

                Log.i(TAG, "Character saved successfully")

            } catch (ex: Exception) {
                Log.e(TAG, ex.printStackTrace().toString())
            }
        }
    }


    override suspend fun deleteCharacter(character: Character) {

        withContext(Dispatchers.IO) {

            try {

                database.favoritesDao().deleteFavorite(favoriteMapper.mapFromDomain(character))

                Log.i(TAG, "Character removed successfully")

            } catch (ex: Exception) {
                Log.e(TAG, ex.printStackTrace().toString())
            }

        }
    }


}