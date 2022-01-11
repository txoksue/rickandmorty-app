package com.paradigma.rickyandmorty.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDao
import com.paradigma.rickyandmorty.data.repository.local.database.FavoritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(): FavoritesDataBase {
        return Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoritesDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @Provides
    fun provideLogDao(database: FavoritesDataBase): FavoritesDao {
        return database.favoritesDao()
    }
}