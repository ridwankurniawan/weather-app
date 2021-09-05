package com.weather.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weather.app.data.datasource.TownRepository
import com.weather.app.data.model.Town

class TownViewModel(private val townRepository: TownRepository) : ViewModel() {


    private val _townResult = MutableLiveData<List<Town>>()
    val townResult: LiveData<List<Town>> = _townResult

    suspend fun getTowns(){
        _townResult.value = townRepository.getTown()
    }
    suspend fun addTown(town: Town){
        townRepository.addTown(town)
        _townResult.value = townRepository.getTown()
    }


}