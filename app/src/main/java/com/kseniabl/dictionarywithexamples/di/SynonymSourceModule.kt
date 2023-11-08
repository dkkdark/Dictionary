package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.remote.RetrofitDefinitionSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitSynonymsSource
import com.kseniabl.dictionarywithexamples.di.annotation.BaseUrlSynonym
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SynonymSourceModule {

    @Provides
    @Singleton
    @BaseUrlSynonym
    fun provideBaseUrl(): String = "https://api.datamuse.com/"

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrlSynonym BASE_URL: String) : RetrofitSynonymsSource = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(RetrofitSynonymsSource::class.java)
}