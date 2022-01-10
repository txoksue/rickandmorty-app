package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.FakeFavoritesRepository
import com.paradigma.rickyandmorty.data.repository.local.favorites.FavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FavoritesRepositoryModule::class]
)
abstract class TestFavoritesRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFavoritesRepository(repo: FakeFavoritesRepository): FavoritesRepository


}
