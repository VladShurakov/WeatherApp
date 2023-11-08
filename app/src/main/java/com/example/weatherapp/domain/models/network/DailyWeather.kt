package com.example.weatherapp.domain.models.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
*   Model for Weather API
**/
@Serializable
data class DailyWeather (
    @SerializedName("daily_units"           ) var dailyUnits           : DailyUnits? = DailyUnits(),
    @SerializedName("daily"                 ) var daily                : Daily?      = Daily()
)