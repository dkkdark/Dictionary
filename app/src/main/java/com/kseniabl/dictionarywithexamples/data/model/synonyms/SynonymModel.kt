package com.kseniabl.dictionarywithexamples.data.model.synonyms

import io.realm.kotlin.types.RealmObject

class SynonymModel: RealmObject {
     var word: String = ""
     var score: Int = 0
 }
