package com.example.weatherapp.feature_weather.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
*   Model for Weather API
**/
@Serializable
data class CurrentWeather (
    @SerializedName("current_units"         ) var currentUnits         : CurrentUnits? = CurrentUnits(),
    @SerializedName("current"               ) var current              : Current?      = Current()
)