package com.paradigma.rickyandmorty.di

import com.paradigma.rickyandmorty.BuildConfig
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RetrofitModule::class]
)
class TestRetrofitModule : RetrofitModule() {
    override fun getBaseUrl() = "http://127.0.0.1:${BuildConfig.TEST_PORT}"
}