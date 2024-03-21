package com.kseniabl.dictionarywithexamples.domain.usecases

import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import javax.inject.Inject

class SearchIconUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(request: String) =
        repository.searchIcons(request)

}