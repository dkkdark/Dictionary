package com.kseniabl.dictionarywithexamples.domain.model

data class WordModel(
    val id: Int,
    val word: String,
    val synonym: List<SynonymEntity>,
    val definition: List<DefinitionEntity>,
    val translation: TranslationEntity
)
