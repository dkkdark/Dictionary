package com.kseniabl.dictionarywithexamples.domain.repository

import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ListModel
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun getDefinition(word: String): Flow<ResultModel<List<DefinitionEntity>>>
    suspend fun getSynonym(word: String): Flow<ResultModel<List<SynonymEntity>>>
    fun translateWithGoogle(text: String): Flow<ResultModel<TranslationEntity>>
    suspend fun searchIcons(request: String): Flow<ResultModel<List<String?>>>

    suspend fun saveWord(
        wordName: String,
        synonyms: List<SynonymEntity>,
        definitions: List<DefinitionEntity>,
        translations: List<TranslationEntity>
    )
    suspend fun getLists(): Flow<List<ListModel>>
}