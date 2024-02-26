package com.kseniabl.dictionarywithexamples.domain.model

data class WordListModel(
    val id: Int,
    val name: String,
    val icon: Int? = null,
    val words: List<WordModel>
)