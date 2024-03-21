package com.kseniabl.dictionarywithexamples.domain.model

import com.kseniabl.dictionarywithexamples.data.local.WordsRealm
import org.mongodb.kbson.ObjectId

data class ListModel(
    var _id: ObjectId,
    var listName: String,
    var listIcon: String,
    var words: List<WordModel>,
)
