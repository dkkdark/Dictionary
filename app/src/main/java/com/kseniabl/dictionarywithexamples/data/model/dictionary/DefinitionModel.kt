package com.kseniabl.dictionarywithexamples.data.model.dictionary

import io.realm.kotlin.types.RealmObject

class DefinitionModel: RealmObject {
    var definition: String? = null
    var example: String? = null
}