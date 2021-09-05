package com.weather.app.data.datasource

import android.util.Log
import com.weather.app.api.IWeather
import com.weather.app.api.RestService
import com.weather.app.data.model.MainWeather
import com.weather.app.data.model.Town
import io.realm.Realm

class TownDatasource() {

    val TAG = "TownDatasource"


    private val realm: Realm = Realm.getDefaultInstance()
    suspend fun fetchTowns(): List<Town> {
        realm.beginTransaction()
        val towns = realm.where(Town::class.java).findAll()
        realm.commitTransaction()
        return towns
    }
    suspend fun add(town: Town) {
        realm.executeTransactionAsync ({
            it.insert(town)
        },{
            Log.d(TAG,"On Success: Data Written Successfully!")
        },{
            Log.d(TAG,"On Error: Error in saving Data!")
        })
    }



}