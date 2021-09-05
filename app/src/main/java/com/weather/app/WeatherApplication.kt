package com.weather.app

import android.app.Application
import androidx.multidex.MultiDexApplication
import io.realm.Realm

class WeatherApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}