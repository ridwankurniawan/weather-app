package com.weather.app.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather (

    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feels_like: Double,
    @SerializedName("pressure")
    val pressure: Long,
    @SerializedName("humidity")
    val humidity: Long,
    @SerializedName("dew_point")
    val dew_point: Double,
    @SerializedName("uvi")
    val uvi: Double,
    @SerializedName("clouds")
    val clouds: Long,
    @SerializedName("visibility")
    val visibility: Long,
    @SerializedName("wind_speed")
    val wind_speed: Double,
    @SerializedName("wind_deg")
    val wind_deg: Long,
    @SerializedName("wind_gust")
    val wind_gust: Double,
    @SerializedName("weather")
    val weather: List<Weather>,

)