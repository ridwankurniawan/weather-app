package com.weather.app.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

open class Town : RealmObject {
    @PrimaryKey
    @Required
    var name: String? = null
    @Required
    var lat: Double? = null
    @Required
    var lon: Double? = null

    constructor() {} // RealmObject subclasses must provide an empty constructor
    constructor(name: String?, lat: Double?, lon: Double?) {
        this.name = name
        this.lat = lat
        this.lon = lon
    }

    override fun toString(): String {
        return if (this.name != null){
            this.name!!
        }else{
            ""
        }
    }
}