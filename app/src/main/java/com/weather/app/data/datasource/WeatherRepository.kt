package com.weather.app.data.datasource

import com.weather.app.api.IWeather
import com.weather.app.api.RestService
import com.weather.app.data.model.MainWeather

class WeatherRepository(private val dataSource: WeatherDatasource) {

    suspend fun dailyWeather(lat: Double,lon: Double): MainWeather {
        return dataSource.oneCall(lat,lon,"hourly,minutely")
    }


}