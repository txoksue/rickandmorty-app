package com.paradigma.rickyandmorty.data.repository.local.database

import androidx.room.*
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

