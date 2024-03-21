package com.kseniabl.dictionarywithexamples.presentation.word_creaton

import androidx.lifecycle.viewModelScope
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ListModel
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.usecases.GoogleTranslationForWordUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadDefinitionFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadSynonymsFromWordNameUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadWordsListsUseCase
import com.kseniabl.dictionarywithexamples.domain.usecases.SaveWordUseCase
import com.kseniabl.dictionarywithexamples.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordCreationViewModel @Inject constructor(
    private val googleTranslationUseCase: GoogleTranslationForWordUseCase,
    private val loadDefinitionUseCase: LoadDefinitionFromWordNameUseCase,
    private val loadSynonymsUseCase: LoadSynonymsFromWordNameUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val loadWordsListsUseCase: LoadWordsListsUseCase
): BaseViewModel(googleTranslationUseCase, loadDefinitionUseCase, loadSynonymsUseCase) {

    private val _states = MutableSharedFlow<WordCreationStates>()
    val wordCreationStates = _states.asSharedFlow()

    private val _lists = MutableStateFlow<List<ListModel>>(listOf())
    val lists = _lists.asStateFlow()

    private val _chosenList = MutableStateFlow<ListModel?>(null)

    val chosenList = combine(_lists, _chosenList) { l, cl ->
        cl ?: l.first()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        viewModelScope.launch {
            loadWordsListsUseCase().collect {
                _lists.value = it
            }
        }
    }

    private fun saveWord(wordName: String?, synonyms: List<SynonymEntity>,
                        definitions: List<DefinitionEntity>, translations: List<TranslationEntity>
    ) = viewModelScope.launch {
        if (wordName.isNullOrEmpty()) {
            _states.emit(WordCreationStates.SpecifyWord)
        }
        else {
            saveWordUseCase(wordName, synonyms, definitions, translations)
            _states.emit(WordCreationStates.ReturnToMainScreen)
        }
    }

    fun processWordCreationEvents(event: WordCreationEvent) {
        when(event) {
            is WordCreationEvent.SaveWord -> {
                saveWord(event.wordName, synonym.value, definition.value, listOf(translation.value))
            }

            is WordCreationEvent.UpdateChosenList -> {
                _chosenList.value = event.list
            }
        }
    }

    sealed interface WordCreationStates {
        object Loading : WordCreationStates
        object SpecifyWord : WordCreationStates
        data class Error(val error: String) : WordCreationStates
        object ReturnToMainScreen : WordCreationStates
    }

    sealed interface WordCreationEvent {
        data class SaveWord(val wordName: String?) : WordCreationEvent
        data class UpdateChosenList(val list: ListModel): WordCreationEvent
    }

}