package com.kseniabl.dictionarywithexamples.data.local

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class ListsRealm: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var listName: String = ""
    var listIcon: String = ""
    var words: RealmList<WordsRealm> = realmListOf()
}