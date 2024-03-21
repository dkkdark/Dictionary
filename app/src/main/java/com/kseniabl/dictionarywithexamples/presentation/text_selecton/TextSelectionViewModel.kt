package com.kseniabl.dictionarywithexamples.presentation.text_selecton

import com.kseniabl.dictionarywithexamples.domain.usecases.GoogleTranslationForWordUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadDefinitionFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadSynonymsFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TextSelectionViewModel @Inject constructor(
    private val googleTranslationUseCase: GoogleTranslationForWordUseCase,
    private val loadDefinitionUseCase: LoadDefinitionFromWordNameUseCase,
    private val loadSynonymsUseCase: LoadSynonymsFromWordNameUseCase
): BaseViewModel(googleTranslationUseCase, loadDefinitionUseCase, loadSynonymsUseCase) {


}