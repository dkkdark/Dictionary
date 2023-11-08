package com.kseniabl.dictionarywithexamples.data

import com.kseniabl.dictionarywithexamples.data.model.dictionary.MeaningModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymResponse
import com.kseniabl.dictionarywithexamples.data.model.synonyms.WordModel
import com.kseniabl.dictionarywithexamples.domain.model.DefinitionEntity
import com.kseniabl.dictionarywithexamples.domain.model.WordEntity
import java.util.ArrayList

fun List<MeaningModel>.mapDictionary(): List<DefinitionEntity> {
    val definitions = arrayListOf<DefinitionEntity>()
    this.forEach {
        it.meanings.forEach {
            it.definitions.forEach { dm ->
                if (dm.definition != null)
                    definitions.add(DefinitionEntity(dm.definition, dm.example))
            }
        }
    }
    return definitions.take(3).ifEmpty { definitions }
}

fun List<WordModel>.mapSynonym(): List<WordEntity> {
    val synonyms = arrayListOf<WordEntity>()
    this.forEach {
        synonyms.add(WordEntity(it.word, it.score))
    }
    return synonyms.take(5).ifEmpty { synonyms }
}