package com.weather.app.data.datasource

import com.weather.app.api.IWeather
import com.weather.app.api.RestService
import com.weather.app.data.model.MainWeather

class WeatherDatasource() {


    val service = RestService()
    suspend fun oneCall(lat: Double,lon: Double,exclude: String): MainWeather {
        return service.create(IWeather::class.java, RestService.API.BASE_URL).oneCall(lat,lon,exclude)
    }



}