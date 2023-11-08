package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.translation.GoogleTranslation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TranslationModule {

    @Provides
    fun provideGoogleTranslation(): GoogleTranslation = GoogleTranslation()
}