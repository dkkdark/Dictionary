package com.kseniabl.dictionarywithexamples.domain.usecases

import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import javax.inject.Inject

class LoadSynonymsFromWordNameUseCase @Inject constructor(
    private val repository: DictionaryRepository
){

    suspend operator fun invoke(word: String) =
        repository.getSynonym(word)

}