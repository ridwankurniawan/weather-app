package com.weather.app.api

import com.weather.app.data.model.MainWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeather {

    @GET("onecall")
    suspend fun oneCall(@Query("lat") lat :Double,@Query("lon") lon :Double,@Query("exclude") exclude :String,@Query("appid") appid :String = "046d84196681996c72078ea61d173ada",@Query("units") units :String = "metric") : MainWeather

}