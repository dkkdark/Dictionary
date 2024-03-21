package com.kseniabl.dictionarywithexamples.domain.model

import org.mongodb.kbson.ObjectId

data class WordModel(
    val id: ObjectId,
    val word: String,
    val synonym: List<SynonymEntity>,
    val definition: List<DefinitionEntity>,
    val translation: List<TranslationEntity>
)
