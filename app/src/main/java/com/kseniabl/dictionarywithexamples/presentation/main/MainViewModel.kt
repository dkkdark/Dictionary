package com.kseniabl.dictionarywithexamples.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.domain.model.ListModel
import com.kseniabl.dictionarywithexamples.domain.usecases.LoadWordsListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loadWordsListsUseCase: LoadWordsListsUseCase
): ViewModel() {

    private val _lists = MutableStateFlow<List<ListModel>>(listOf())
    val lists = _lists.asStateFlow()

   init {
       viewModelScope.launch {
           loadWordsListsUseCase().collect {
               _lists.emit(it)
           }
       }
   }

}