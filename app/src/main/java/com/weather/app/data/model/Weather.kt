package com.weather.app.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Weather(
    @SerializedName("id")
    val id: Long,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
)