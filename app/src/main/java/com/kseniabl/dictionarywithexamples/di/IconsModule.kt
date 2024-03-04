package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.remote.IconsSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitDefinitionSource
import com.kseniabl.dictionarywithexamples.di.annotation.BaseUrlDefinition
import com.kseniabl.dictionarywithexamples.di.annotation.BaseUrlIcons
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class IconsModule {

    @Provides
    @Singleton
    @BaseUrlIcons
    fun provideBaseUrl(): String = "https://api.iconify.design/"

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrlIcons BASE_URL: String) : IconsSource = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(IconsSource::class.java)
}