package com.paradigma.rickyandmorty.di


import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepositoryImpl
import com.paradigma.rickyandmorty.data.repository.remote.characters.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class CharacterModule {

    @Singleton
    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}