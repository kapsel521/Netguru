package com.example.netguruapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ArchivedNotes (
    @PrimaryKey
    var id:Int? = null,
    var title:String? = null,
    var shopList:String? = null
    ) : RealmObject()
