package com.kseniabl.dictionarywithexamples.presentation.list_creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.domain.usecases.SearchIconUseCase
import com.kseniabl.dictionarywithexamples.presentation.common.processModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    private val searchIconUseCase: SearchIconUseCase,
    private val realm: Realm
): ViewModel() {

    private val _iconsUrls = MutableStateFlow<List<String?>>(listOf())
    val iconsUrls = _iconsUrls.asStateFlow()

    private val _states = MutableSharedFlow<ListCreationStates>()
    val states = _states.asSharedFlow()


    private fun getIcons(request: String) = viewModelScope.launch {
        searchIconUseCase(request)
            .collect {
                Log.e("qqq", "it ${it.data} ${it.message}")
                it.processModel(getValue = { _iconsUrls }, state = _states, stateErrorVal = ListCreationStates.Error(it.message),
                    stateLoadingVal = ListCreationStates.Loading)
            }
    }

    private fun addList(name: String, icon: String?) = viewModelScope.launch {
        if (icon.isNullOrEmpty()) {
            _states.emit(ListCreationStates.IconIsNull)
        }
        if (name.isEmpty()) {
            _states.emit(ListCreationStates.NameIsEmpty)
        }
        if (name.isNotEmpty() && !icon.isNullOrEmpty()) {
            realm.write {
                copyToRealm(ListsRealm().apply {
                    listName = name
                    listIcon = icon
                    words = realmListOf()
                })
            }
            _states.emit(ListCreationStates.ReturnToMainScreen)
        }
    }

    fun processEvents(event: ListCreationEvent) {
        when (event) {
            is ListCreationEvent.SearchIcons -> {
                getIcons(event.request)
            }
            is ListCreationEvent.AddList -> {
                addList(event.name, event.icon)
            }
        }
    }

    sealed interface ListCreationStates {
        object Loading : ListCreationStates
        object SuccessSaving : ListCreationStates
        data class Error(val error: String?) : ListCreationStates
        object NotCorrectDate : ListCreationStates
        object IconIsNull: ListCreationStates
        object NameIsEmpty: ListCreationStates
        object ReturnToMainScreen: ListCreationStates
    }

    sealed interface ListCreationEvent {
        data class SearchIcons(val request: String) : ListCreationEvent
        data class AddList(val name: String, val icon: String?): ListCreationEvent
    }

}