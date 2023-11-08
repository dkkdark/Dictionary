package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.repository.DictionaryRepositoryImpl
import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindDictionaryRepository(
        repository: DictionaryRepositoryImpl
    ): DictionaryRepository


}