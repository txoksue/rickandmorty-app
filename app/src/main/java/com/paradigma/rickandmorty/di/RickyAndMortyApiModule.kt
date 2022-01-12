package com.paradigma.rickandmorty.di

import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiServiceImpl
import com.paradigma.rickandmorty.data.repository.remote.api.RickyAndMortyApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RickyAndMortyApiModule {

    @Singleton
    @Binds
    abstract fun bindRickyAndMortyApiService(impl: RickyAndMortyApiServiceImpl): RickyAndMortyApiService
}