package com.example.weatherapp.feature_weather.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
*   Model for Weather API
**/
@Serializable
data class HourlyWeather (
    @SerializedName("hourly_units"          ) var hourlyUnits          : HourlyUnits? = HourlyUnits(),
    @SerializedName("hourly"                ) var hourly               : Hourly?      = Hourly()
)