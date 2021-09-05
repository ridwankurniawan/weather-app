package com.weather.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.app.data.datasource.WeatherRepository
import com.weather.app.data.model.MainWeather

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {


    private val _weatherResult = MutableLiveData<MainWeather>()
    val weatherResult: LiveData<MainWeather> = _weatherResult

    suspend fun dailyWeather(lat: Double,lon: Double){
        _weatherResult.value = weatherRepository.dailyWeather(lat,lon)
    }


}