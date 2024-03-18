package com.kseniabl.dictionarywithexamples.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val realm: Realm
): ViewModel() {

   val lists = realm.query<ListsRealm>()
       .asFlow()
       .map {
           it.list.toList()
       }
       .stateIn(
           viewModelScope,
           SharingStarted.WhileSubscribed(),
           emptyList()
       )

}