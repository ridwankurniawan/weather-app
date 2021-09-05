package com.weather.app.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weather.app.data.datasource.TownDatasource
import com.weather.app.data.datasource.TownRepository
import com.weather.app.data.datasource.WeatherDatasource
import com.weather.app.data.datasource.WeatherRepository

class WeatherViewModelFactory() : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                    weatherRepository = WeatherRepository(
                            dataSource = WeatherDatasource()
                    )
            ) as T
        }
        if (modelClass.isAssignableFrom(TownViewModel::class.java)) {
            return TownViewModel(
                townRepository = TownRepository(
                    dataSource = TownDatasource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}