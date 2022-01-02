package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.data.mapper.CharacterDataMapper
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.domain.Character
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class MapperModule {


    @Singleton
    @Binds
    abstract fun bindCharacterDataMapper(mapper: CharacterDataMapper): Mapper<CharacterDTO, Character>


}