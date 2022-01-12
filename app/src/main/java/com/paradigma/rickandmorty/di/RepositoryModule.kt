package com.paradigma.rickandmorty.di


import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickandmorty.data.repository.local.favorites.FavoritesRepositoryImpl
import com.paradigma.rickandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickandmorty.data.repository.remote.location.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository

    @Singleton
    @Binds
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(impl: FavoritesRepositoryImpl): FavoritesRepository
}