package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.FakeCharacterRepository
import com.paradigma.rickyandmorty.FakeFavoritesRepository
import com.paradigma.rickyandmorty.FakeLocationRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
abstract class TestRepositoryModule {
    
    @Singleton
    @Binds
    abstract fun bindCharacterRepository(repo: FakeCharacterRepository): CharacterRepository

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(repo: FakeFavoritesRepository): FavoritesRepository

    @Singleton
    @Binds
    abstract fun bindLocationRepository(repo: FakeLocationRepository): LocationRepository
}
