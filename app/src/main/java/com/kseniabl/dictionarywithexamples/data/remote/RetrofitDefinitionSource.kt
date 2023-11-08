package com.kseniabl.dictionarywithexamples.data.remote

import com.kseniabl.dictionarywithexamples.data.model.dictionary.MeaningModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitDefinitionSource {

    // https://api.dictionaryapi.dev/api/v2
    @GET("entries/en/{word}")
    suspend fun getEnglishDefinition(@Path("word") word: String): Response<List<MeaningModel>>

}