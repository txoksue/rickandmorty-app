package com.paradigma.rickyandmorty.di


import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepository
import com.paradigma.rickyandmorty.data.repository.remote.location.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocationRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository
}