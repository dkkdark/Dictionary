package com.kseniabl.dictionarywithexamples.di

import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.data.local.WordsRealm
import com.kseniabl.dictionarywithexamples.data.model.dictionary.DefinitionModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymModel
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RealmModule {

    @Provides
    @Singleton
    fun provideRealmConfig() =
        RealmConfiguration.create(schema = setOf(
            ListsRealm::class, WordsRealm::class, SynonymModel::class,
            TranslationEntity::class, DefinitionModel::class
        ))

    @Provides
    @Singleton
    fun provideRealm(config: RealmConfiguration) = Realm.open(config)

}