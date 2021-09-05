package com.weather.app.data.model

import com.google.gson.annotations.SerializedName

data class DailyWeather (

    @SerializedName("dt")
    val dt: Long,
    @SerializedName("sunrise")
    val sunrise: Long,
    @SerializedName("sunset")
    val sunset: Long,
    @SerializedName("moonrise")
    val moonrise: Long,
    @SerializedName("moonset")
    val moonset: Long,
    @SerializedName("moon_phase")
    val moon_phase: Double,
    @SerializedName("pressure")
    val pressure: Long,
    @SerializedName("humidity")
    val humidity: Long,
    @SerializedName("dew_point")
    val dew_point: Double,
    @SerializedName("wind_speed")
    val wind_speed: Double,
    @SerializedName("wind_deg")
    val wind_deg: Long,
    @SerializedName("wind_gust")
    val wind_gust: Double,
    @SerializedName("clouds")
    val clouds: Long,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("uvi")
    val uvi: Double,

    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("weather")
    val weather: List<Weather>,

)