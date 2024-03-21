package com.kseniabl.dictionarywithexamples.domain.usecases

import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import javax.inject.Inject

class SaveWordUseCase @Inject constructor(
    val repository: DictionaryRepository
) {
    suspend operator fun invoke(wordName: String, synonyms: List<SynonymEntity>,
                                definitions: List<DefinitionEntity>, translations: List<TranslationEntity>) {
        repository.saveWord(wordName, synonyms, definitions, translations)
    }
}