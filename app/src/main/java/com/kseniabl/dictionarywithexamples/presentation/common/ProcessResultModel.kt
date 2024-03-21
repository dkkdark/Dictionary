package com.kseniabl.dictionarywithexamples.presentation.common

import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
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
            if (this.data != null)
                getValue().value = this.data
            else state.emit(stateErrorVal)
        }
        is ResultModel.Error -> { state.emit(stateErrorVal) }
        is ResultModel.Loading -> { state.emit(stateLoadingVal) }
    }
}