package com.kseniabl.dictionarywithexamples.domain.model

sealed class ResultModel<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T? = null) : ResultModel<T>(data = data)

    class Error<T>(errorMessage: String?) : ResultModel<T>(message = errorMessage)

    class Loading<T> : ResultModel<T>()
}