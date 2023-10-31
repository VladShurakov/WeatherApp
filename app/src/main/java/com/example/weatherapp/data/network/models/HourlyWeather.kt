package com.example.weatherapp.data.network.models

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