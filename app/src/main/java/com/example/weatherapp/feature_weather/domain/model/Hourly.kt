package com.example.weatherapp.feature_weather.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Hourly (
  @SerializedName("time"           ) var time          : ArrayList<String> = arrayListOf(),
  @SerializedName("temperature_2m" ) var temp          : ArrayList<Double> = arrayListOf(),
  @SerializedName("weathercode"    ) var weatherCode   : ArrayList<Int>    = arrayListOf()
)