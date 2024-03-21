package com.kseniabl.dictionarywithexamples.data.repository

import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.data.local.WordsRealm
import com.kseniabl.dictionarywithexamples.data.mapDictionary
import com.kseniabl.dictionarywithexamples.data.mapSynonym
import com.kseniabl.dictionarywithexamples.data.mapToUrls
import com.kseniabl.dictionarywithexamples.data.remote.IconsSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitDefinitionSource
import com.kseniabl.dictionarywithexamples.data.remote.RetrofitSynonymsSource
import com.kseniabl.dictionarywithexamples.data.toDefinitionModel
import com.kseniabl.dictionarywithexamples.data.toListModel
import com.kseniabl.dictionarywithexamples.data.toSynonymModel
import com.kseniabl.dictionarywithexamples.data.translation.GoogleTranslation
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ResultModel
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import com.kseniabl.dictionarywithexamples.domain.repository.DictionaryRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val definitionSource: RetrofitDefinitionSource,
    private val synonymsSource: RetrofitSynonymsSource,
    private val googleTranslation: GoogleTranslation,
    private val iconsSource: IconsSource,
    private val realm: Realm
): DictionaryRepository {

    override suspend fun searchIcons(request: String) = processData(
        data = {
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

    override suspend fun saveWord(wordName: String, synonyms: List<SynonymEntity>,
                                  definitions: List<DefinitionEntity>, translations: List<TranslationEntity>) {
        realm.write {
            copyToRealm(WordsRealm().apply {
                word = wordName
                synonym = synonyms.toSynonymModel().toRealmList()
                definition = definitions.toDefinitionModel().toRealmList()
                translation = translations.toRealmList()
            })
        }
    }

    override suspend fun getLists() =
        realm.query<ListsRealm>().asFlow().map {
            it.list.toList().toListModel()
        }


}

inline fun <R,T> processData(
    crossinline data: suspend () -> Response<R>,
    crossinline mapper: (R) -> List<T>
) = flow {
    emit(ResultModel.Loading())
    //Log.e("qqq", "ggg ${data()}")
    val response = data()

    if (response.isSuccessful && response.body() != null) {
        val body = response.body()!!
        emit(ResultModel.Success(mapper(body)))
    }
    else {
        emit(ResultModel.Error("Unsuccessful response"))
    }
}