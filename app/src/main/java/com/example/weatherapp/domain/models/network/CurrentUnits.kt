package com.example.weatherapp.domain.models.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnits (
  @SerializedName("temperature_2m"       ) var temp                : String? = null,
  @SerializedName("relativehumidity_2m"  ) var humidity            : String? = null,
  @SerializedName("apparent_temperature" ) var apparentTemp        : String? = null,
  @SerializedName("precipitation"        ) var precipitation       : String? = null,
  @SerializedName("weathercode"          ) var weatherCode         : String? = null,
  @SerializedName("windspeed_10m"        ) var windSpeed           : String? = null
)