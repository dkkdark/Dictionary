package com.kseniabl.dictionarywithexamples.domain.repository

import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordEntity
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

interface DictionaryRepository {
    suspend fun getDefinition(word: String): Flow<ResultModel<ArrayList<DefinitionEntity>>>
    suspend fun getSynonym(word: String): Flow<ResultModel<ArrayList<WordEntity>>>
    fun translateWithGoogle(text: String): Flow<ResultModel<TranslationEntity>>
}