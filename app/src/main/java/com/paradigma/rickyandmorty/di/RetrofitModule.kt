package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.BuildConfig
import com.paradigma.rickyandmorty.data.repository.remote.api.RickyAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@InstallIn(SingletonComponent::class)
@Module
open class RetrofitModule {

    open fun getBaseUrl () = BuildConfig.BASE_URL

    @Provides
    @BaseUrl
    fun provideBaseUrl() = getBaseUrl()

    @Provides
    fun provideRetrofitClient(baseUrl: String): Retrofit {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
            //.retryOnConnectionFailure(true)
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    fun provideRickyAndMortyApi(@BaseUrl baseUrl: String): RickyAndMortyApi {
        return provideRetrofitClient(baseUrl).create(RickyAndMortyApi::class.java)
    }

}