package com.paradigma.rickandmorty.data.repository.local.database

import androidx.room.*
import com.paradigma.rickandmorty.data.repository.local.database.entity.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

