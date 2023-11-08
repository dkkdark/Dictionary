package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.remote.RetrofitDefinitionSource
import com.kseniabl.dictionarywithexamples.di.annotation.BaseUrlDefinition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DefinitionSourceModule {

    @Provides
    @Singleton
    @BaseUrlDefinition
    fun provideBaseUrl(): String = "https://api.dictionaryapi.dev/api/v2/"

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrlDefinition BASE_URL: String) : RetrofitDefinitionSource = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(RetrofitDefinitionSource::class.java)
}