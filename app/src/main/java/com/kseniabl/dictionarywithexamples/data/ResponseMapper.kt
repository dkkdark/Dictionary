package com.kseniabl.dictionarywithexamples.data

import com.kseniabl.dictionarywithexamples.data.model.dictionary.MeaningModel
import com.kseniabl.dictionarywithexamples.data.model.icons.IconsModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymModel
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.SynonymEntity

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