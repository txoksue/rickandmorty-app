package com.paradigma.rickandmorty.di

import com.paradigma.rickandmorty.data.mapper.CharacterDataMapper
import com.paradigma.rickandmorty.data.mapper.FavoriteDataMapper
import com.paradigma.rickandmorty.data.mapper.LocationDataMapper
import com.paradigma.rickandmorty.data.mapper.Mapper
import com.paradigma.rickandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickandmorty.domain.Character
import com.paradigma.rickandmorty.domain.Location
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

    @Singleton
    @Binds
    abstract fun bindLocationDataMapper(mapper: LocationDataMapper): Mapper<LocationDTO, Location>

    @Singleton
    @Binds
    abstract fun bindFavoriteDataMapper(mapper: FavoriteDataMapper): Mapper<Favorite, Character>


}