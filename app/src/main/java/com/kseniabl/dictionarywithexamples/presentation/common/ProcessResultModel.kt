package com.kseniabl.dictionarywithexamples.presentation.common

import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.presentation.text_selecton.TextSelectionStates
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

suspend inline fun <T, F> ResultModel<T>.processModel(
    crossinline getValue: () -> MutableStateFlow<T>,
    state: MutableSharedFlow<F>,
    stateErrorVal: F,
    stateLoadingVal: F
) {
    when(this) {
        is ResultModel.Success -> {
            getValue().value = this.data!!
        }
        is ResultModel.Error -> { state.emit(stateErrorVal) }
        is ResultModel.Loading -> { state.emit(stateLoadingVal) }
    }
}