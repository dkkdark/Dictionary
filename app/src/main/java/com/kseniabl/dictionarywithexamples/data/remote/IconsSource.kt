package com.kseniabl.dictionarywithexamples.data.remote

import com.kseniabl.dictionarywithexamples.data.model.icons.IconsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface IconsSource {

    // https://api.iconify.design
    @GET("search")
    suspend fun searchIcons(@Query("query") word: String, @Query("limit") limit: Int): Response<IconsModel>

}