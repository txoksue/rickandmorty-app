package com.paradigma.rickyandmorty.data.repository.local.database

import androidx.room.*
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM Favorite")
    fun getFavorites(): List<Favorite>

    @Delete
    fun deleteFavorite(character: Favorite)

    @Insert
    fun insertFavorite(vararg characters: Favorite)
}
