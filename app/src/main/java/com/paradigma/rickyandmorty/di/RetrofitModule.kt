package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.common.config.Constants
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideRetrofitClient(): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun provideRickyAndMortyApi(): RickyAndMortyApi {
        return provideRetrofitClient().create(RickyAndMortyApi::class.java)
    }

}