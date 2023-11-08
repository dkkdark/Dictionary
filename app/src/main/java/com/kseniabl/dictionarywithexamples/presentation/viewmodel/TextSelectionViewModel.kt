package com.kseniabl.dictionarywithexamples.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordEntity
import com.kseniabl.dictionarywithexamples.domain.usecases.GoogleTranslationForWordUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadDefinitionFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadSynonymsFromWordNameUseCase
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

    private val _translation = MutableStateFlow(TranslationEntity(""))
    val translation = _translation.asStateFlow()

    private val _definition = MutableStateFlow<List<DefinitionEntity>>(listOf())
    val definition = _definition.asStateFlow()

    private val _synonym = MutableStateFlow<List<WordEntity>>(listOf())
    val synonym = _synonym.asStateFlow()

    private val _states = MutableSharedFlow<TextSelectionStates>()
    val states = _states.asSharedFlow()

    private fun getInfoForWord(word: String) = viewModelScope.launch {
        val translation = googleTranslationUseCase(word)
        translation.collect {
            it.processModel { _translation }
        }
    }

    private fun getSynonym(word: String) = viewModelScope.launch {
        val synonym = loadSynonymsUseCase(word)
        synonym.collect {
            it.processModel { _synonym }
        }
    }

    private fun getDefinition(word: String) = viewModelScope.launch {
        val definition = loadDefinitionUseCase(word)
        definition.collect {
            it.processModel { _definition }
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

    private suspend inline fun <T> ResultModel<T>.processModel(
        crossinline getValue: () -> MutableStateFlow<T>
    ) {
        when(this) {
            is ResultModel.Success -> {
                getValue().value = this.data!!
            }
            is ResultModel.Error -> { _states.emit(TextSelectionStates.Error(this.message!!)) }
            is ResultModel.Loading -> { _states.emit(TextSelectionStates.Loading) }
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