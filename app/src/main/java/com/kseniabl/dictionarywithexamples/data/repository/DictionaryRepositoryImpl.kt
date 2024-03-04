package com.kseniabl.dictionarywithexamples.data.repository

import android.util.Log
import com.kseniabl.dictionarywithexamples.data.mapDictionary
import com.kseniabl.dictionarywithexamples.data.mapSynonym
import com.kseniabl.dictionarywithexamples.data.mapToUrls
import com.kseniabl.dictionarywithexamples.data.remote.IconsSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitDefinitionSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitSynonymsSource
import com.kseniabl.dictionarywithexamples.data.translation.GoogleTranslation
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val definitionSource: RetrofitDefinitionSource,
    private val synonymsSource: RetrofitSynonymsSource,
    private val googleTranslation: GoogleTranslation,
    private val iconsSource: IconsSource
): DictionaryRepository {

    override suspend fun searchIcons(request: String) = processData (
        data =  {
            iconsSource.searchIcons(request, 3)
        },
        mapper = {
            it.mapToUrls()
        }
    )

    override suspend fun getDefinition(word: String) = processData(
        data = {
            definitionSource.getEnglishDefinition(word)
        },
        mapper = {
            it.mapDictionary()
        }
    )

    override suspend fun getSynonym(word: String) = processData(
        data = {
            synonymsSource.getSynonyms(word)
        },
        mapper = {
            it.mapSynonym()
        }
    )

    override fun translateWithGoogle(text: String) =
        googleTranslation.downloadModelAndTranslate(text)

}

inline fun <R,T> processData(
    crossinline data: suspend () -> Response<R>,
    crossinline mapper: (R) -> List<T>
) = flow {
    emit(ResultModel.Loading())
    val response = data()
    Log.e("qqq", "response $response")

    if (response.isSuccessful && response.body() != null) {
        val body = response.body()!!
        emit(ResultModel.Success(mapper(body)))
    }
    else {
        emit(ResultModel.Error("Unsuccessful response"))
    }
}