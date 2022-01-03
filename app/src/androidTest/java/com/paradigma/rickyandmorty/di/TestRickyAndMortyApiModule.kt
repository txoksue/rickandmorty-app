package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.FakeRickyAndMortyApiService
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RickyAndMortyApiModule::class]
)
abstract class TestRickyAndMortyApiModule {

    @Singleton
    @Binds
    abstract fun bindRickyAndMortyApiService(impl: FakeRickyAndMortyApiService): RickyAndMortyApiService
}