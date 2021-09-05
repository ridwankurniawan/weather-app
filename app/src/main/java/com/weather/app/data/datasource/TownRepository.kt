package com.weather.app.data.datasource

import com.weather.app.api.IWeather
import com.weather.app.api.RestService
import com.weather.app.data.model.MainWeather
import com.weather.app.data.model.Town

class TownRepository(private val dataSource: TownDatasource) {

    suspend fun getTown(): List<Town> {
        return dataSource.fetchTowns()
    }
    suspend fun addTown(town: Town) {
        return dataSource.add(town)
    }


}