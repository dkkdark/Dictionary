package com.kseniabl.dictionarywithexamples.data

import com.kseniabl.dictionarywithexamples.data.model.dictionary.MeaningModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymResponse
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordEntity
import java.util.ArrayList

fun List<MeaningModel>.mapDictionary(): ArrayList<DefinitionEntity> {
    val definitions = arrayListOf<DefinitionEntity>()
    this.forEach {
        it.meanings.forEach {
            it.definitions.forEach { dm ->
                definitions.add(DefinitionEntity(dm.definition, dm.example))
            }
        }
    }
    return definitions.take(3).ifEmpty { definitions } as ArrayList<DefinitionEntity>
}

fun SynonymResponse.mapSynonym(): ArrayList<WordEntity> {
    val synonyms = arrayListOf<WordEntity>()
    this.words.take(5).ifEmpty { this.words }
        .forEach {
        synonyms.add(WordEntity(it.word, it.score))
    }
    return synonyms
}