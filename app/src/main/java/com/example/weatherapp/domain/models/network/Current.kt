package com.example.weatherapp.domain.models.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Current (
  @SerializedName("time"                 ) var time                : String? = null,
  @SerializedName("temperature_2m"       ) var temp                : Double? = null,
  @SerializedName("relativehumidity_2m"  ) var humidity            : Long?    = null,
  @SerializedName("apparent_temperature" ) var apparentTemp        : Double? = null,
  @SerializedName("precipitation"        ) var precipitation       : Double?    = null,
  @SerializedName("weathercode"          ) var weatherCode         : Int?    = null,
  @SerializedName("windspeed_10m"        ) var windSpeed           : Double? = null
)