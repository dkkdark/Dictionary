package com.kseniabl.dictionarywithexamples.presentation.text_selecton

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.usecases.GoogleTranslationForWordUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadDefinitionFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadSynonymsFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.presentation.common.processModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextSelectionViewModel @Inject constructor(
    val googleTranslationUseCase: GoogleTranslationForWordUseCase,
    val loadDefinitionUseCase: LoadDefinitionFromWordNameUseCase,
    val loadSynonymsUseCase: LoadSynonymsFromWordNameUseCase
): ViewModel() {

    private val _translation = MutableStateFlow(TranslationEntity())
    val translation = _translation.asStateFlow()

    private val _definition = MutableStateFlow<List<DefinitionEntity>>(listOf())
    val definition = _definition.asStateFlow()

    private val _synonym = MutableStateFlow<List<SynonymEntity>>(listOf())
    val synonym = _synonym.asStateFlow()

    private val _states = MutableSharedFlow<TextSelectionStates>()
    val states = _states.asSharedFlow()

    private fun getInfoForWord(word: String) = viewModelScope.launch {
        val translation = googleTranslationUseCase(word)
        translation.collect {
            it.processModel(getValue = { _translation }, state = _states, stateErrorVal = TextSelectionStates.Error(it.message!!),
                stateLoadingVal = TextSelectionStates.Loading)
        }
    }

    private fun getSynonym(word: String) = viewModelScope.launch {
        val synonym = loadSynonymsUseCase(word)
        synonym.collect {
            it.processModel(getValue = { _synonym }, state = _states, stateErrorVal = TextSelectionStates.Error(it.message!!),
                stateLoadingVal = TextSelectionStates.Loading)
        }
    }

    private fun getDefinition(word: String) = viewModelScope.launch {
        val definition = loadDefinitionUseCase(word)
        definition.collect {
            it.processModel(getValue = { _definition }, state = _states, stateErrorVal = TextSelectionStates.Error(it.message!!),
                stateLoadingVal = TextSelectionStates.Loading)
        }
    }

    fun processEvents(event: TextSelectionEvent) {
        when(event) {
            is TextSelectionEvent.GetInfoForWord -> {
                getInfoForWord(event.word)
                getDefinition(event.word)
                getSynonym(event.word)
            }
        }
    }

}

sealed interface TextSelectionStates {
    object Loading : TextSelectionStates
    object SuccessSaving : TextSelectionStates
    data class Error(val error: String) : TextSelectionStates
    object NotCorrectDate : TextSelectionStates
}

sealed interface TextSelectionEvent {
    data class GetInfoForWord(val word: String) : TextSelectionEvent
}