package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.data.mapper.CharacterDataMapper
import com.paradigma.rickyandmorty.data.mapper.FavoriteDataMapper
import com.paradigma.rickyandmorty.data.mapper.LocationDataMapper
import com.paradigma.rickyandmorty.data.mapper.Mapper
import com.paradigma.rickyandmorty.data.repository.local.database.entity.Favorite
import com.paradigma.rickyandmorty.data.repository.remote.api.model.CharacterDTO
import com.paradigma.rickyandmorty.data.repository.remote.api.model.LocationDTO
import com.paradigma.rickyandmorty.domain.Character
import com.paradigma.rickyandmorty.domain.Location
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