package com.example.weatherapp.feature_weather.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits (
  @SerializedName("time"               ) var time             : String? = null,
  @SerializedName("weathercode"        ) var weatherCode      : String? = null,
  @SerializedName("temperature_2m_max" ) var tempMax          : String? = null,
  @SerializedName("temperature_2m_min" ) var tempMin          : String? = null
)