package com.kseniabl.dictionarywithexamples.domain.usecases

import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import javax.inject.Inject

class LoadWordsListsUseCase @Inject constructor(
    val repository: DictionaryRepository
) {
    suspend operator fun invoke() =
        repository.getLists()
}