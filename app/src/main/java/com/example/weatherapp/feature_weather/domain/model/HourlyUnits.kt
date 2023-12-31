package com.example.weatherapp.feature_weather.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyUnits (
  @SerializedName("time"           ) var time          : String? = null,
  @SerializedName("temperature_2m" ) var temp          : String? = null,
  @SerializedName("weathercode"    ) var weatherCode   : String? = null
)