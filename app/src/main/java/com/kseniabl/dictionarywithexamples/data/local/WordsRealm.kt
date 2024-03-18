package com.kseniabl.dictionarywithexamples.data.local

import com.kseniabl.dictionarywithexamples.data.model.dictionary.DefinitionModel
import com.kseniabl.dictionarywithexamples.data.model.synonyms.SynonymModel
import com.kseniabl.dictionarywithexamples.domain.model.TranslationEntity
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId

class WordsRealm: RealmObject {
    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var word: String = ""
    var synonym: RealmList<SynonymModel> = realmListOf()
    var definition: RealmList<DefinitionModel> = realmListOf()
    var translation: TranslationEntity? = null
}