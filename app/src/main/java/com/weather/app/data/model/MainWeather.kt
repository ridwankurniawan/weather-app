package com.weather.app.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MainWeather(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double,
        @SerializedName("timezone")
        val timezone: String,
        @SerializedName("timezone_offset")
        val timezone_offset: Int,

        @SerializedName("current")
        val current: CurrentWeather,
        @SerializedName("daily")
        val daily: List<DailyWeather>,
)