package com.kseniabl.dictionarywithexamples.data.remote

import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitSynonymsSource {

    // https://api.datamuse.com
    @GET("words")
    suspend fun getSynonyms(@Query("rel_syn") word: String): Response<SynonymResponse>

}