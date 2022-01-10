package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class FavoritesRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}