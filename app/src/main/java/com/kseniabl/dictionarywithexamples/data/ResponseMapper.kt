package com.kseniabl.dictionarywithexamples.data

import com.kseniabl.dictionarywithexamples.data.local.ListsRealm
import com.kseniabl.dictionarywithexamples.data.local.WordsRealm
import com.kseniabl.dictionarywithexamples.data.model.dictionary.DefinitionModel
import com.kseniabl.dictionarywithexamples.data.model.dictionary.MeaningModel
import com.kseniabl.dictionarywithexamples.data.model.icons.IconsModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymModel
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.ListModel
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordModel
import io.realm.kotlin.types.RealmList

fun List<MeaningModel>.mapDictionary(): List<DefinitionEntity> {
    val definitions = arrayListOf<DefinitionEntity>()
    this.forEach {
        it.meanings.forEach {
            it.definitions.forEach { dm ->
                if (dm.definition != null)
                    definitions.add(DefinitionEntity(dm.definition!!, dm.example))
            }
        }
    }
    return definitions.take(3).ifEmpty { definitions }
}

fun List<SynonymModel>.mapSynonym(): List<SynonymEntity> {
    val synonyms = arrayListOf<SynonymEntity>()
    this.forEach {
        synonyms.add(SynonymEntity(it.word, it.score))
    }
    return synonyms.take(5).ifEmpty { synonyms }
}

fun IconsModel.mapToUrls(color: String = "white"): List<String?> {
    return this.icons.map { prefixAndIcon ->
        val parts = prefixAndIcon.split(":")
        if (parts.size == 2) "https://api.iconify.design/${parts[0]}/${parts[1]}.svg?color=${color}"
        else null
    }
}

fun List<SynonymEntity>.toSynonymModel(): List<SynonymModel> =
    this.map {
        val synonymModel = SynonymModel()
        synonymModel.word = it.word
        synonymModel.score = it.score
        synonymModel
    }

fun RealmList<SynonymModel>.toSynonymModel(): List<SynonymEntity> =
    this.map {
        SynonymEntity(
            it.word,
            it.score
        )
    }

fun List<DefinitionEntity>.toDefinitionModel(): List<DefinitionModel> =
    this.map {
        val definition = DefinitionModel()
        definition.definition = it.definition
        definition.example = it.example
        definition
    }

fun RealmList<DefinitionModel>.toDefinitionModel(): List<DefinitionEntity> =
    this.map {
        DefinitionEntity(
            it.definition ?: "",
            it.example
        )
    }

fun List<ListsRealm>.toListModel(): List<ListModel> =
    this.map {
        ListModel(
            it._id,
            it.listName,
            it.listIcon,
            it.words.toWordModel()
        )
    }

fun RealmList<WordsRealm>.toWordModel(): List<WordModel> =
    this.map {
        WordModel(
            it._id,
            it.word,
            it.synonym.toSynonymModel(),
            it.definition.toDefinitionModel(),
            it.translation
        )
    }