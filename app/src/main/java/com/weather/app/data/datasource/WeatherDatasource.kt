package com.weather.app.data.datasource

import android.content.Context
import com.weather.app.api.IWeather
import com.weather.app.api.RestService
import com.weather.app.data.model.MainWeather

class WeatherDatasource(private val context: Context) {


    val service = RestService()
    suspend fun oneCall(lat: Double,lon: Double,exclude: String): MainWeather {
        return service.create(IWeather::class.java, RestService.API.BASE_URL,true,context).oneCall(lat,lon,exclude)
    }



}