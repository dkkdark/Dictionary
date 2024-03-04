package com.kseniabl.dictionarywithexamples.presentation.list_creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.dictionarywithexamples.domain.usecases.SearchIconUseCase
import com.kseniabl.dictionarywithexamples.presentation.common.processModel
import com.kseniabl.dictionarywithexamples.presentation.text_selecton.TextSelectionStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    private val searchIconUseCase: SearchIconUseCase
): ViewModel() {

    private val _iconsUrls = MutableStateFlow<List<String?>>(listOf())
    val iconsUrls = _iconsUrls.asStateFlow()

    private val _states = MutableSharedFlow<ListCreationStates>()
    val states = _states.asSharedFlow()

    @OptIn(FlowPreview::class)
    private fun getIcons(request: String) = viewModelScope.launch {
        val icons = searchIconUseCase(request).debounce { 2500L }
        icons.collect {
            Log.e("qqq", "it ${it.data} ${it.message}")
            it.processModel(getValue = { _iconsUrls }, state = _states, stateErrorVal = ListCreationStates.Error(it.message),
                stateLoadingVal = ListCreationStates.Loading)
        }
    }

    fun processEvents(event: ListCreationEvent) {
        when (event) {
            is ListCreationEvent.SearchIcons -> {
                getIcons(event.request)
            }
        }
    }

    sealed interface ListCreationStates {
        object Loading : ListCreationStates
        object SuccessSaving : ListCreationStates
        data class Error(val error: String?) : ListCreationStates
        object NotCorrectDate : ListCreationStates
    }

    sealed interface ListCreationEvent {
        data class SearchIcons(val request: String) : ListCreationEvent
    }

}