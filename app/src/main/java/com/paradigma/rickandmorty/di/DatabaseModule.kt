package com.paradigma.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.paradigma.rickandmorty.common.config.Constants
import com.paradigma.rickandmorty.data.repository.local.database.FavoritesDao
import com.paradigma.rickandmorty.data.repository.local.database.FavoritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): FavoritesDataBase {
        return Room.databaseBuilder(
            appContext,
            FavoritesDataBase::class.java,
            Constants.FAVORITE_DATA_BASE
        ).build()
    }

    @Provides
    fun provideLogDao(database: FavoritesDataBase): FavoritesDao {
        return database.favoritesDao()
    }
}