package com.kseniabl.dictionarywithexamples.domain.usecases

import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import javax.inject.Inject

class GoogleTranslationForWordUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {

    operator fun invoke(text: String) =
        repository.translateWithGoogle(text)

}