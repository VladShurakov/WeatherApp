package com.example.weatherapp.domain.models.network

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Daily (
  @SerializedName("time"               ) var time             : ArrayList<String> = arrayListOf(),
  @SerializedName("weathercode"        ) var weatherCode      : ArrayList<Int>    = arrayListOf(),
  @SerializedName("temperature_2m_max" ) var tempMax          : ArrayList<Double> = arrayListOf(),
  @SerializedName("temperature_2m_min" ) var tempMin          : ArrayList<Double> = arrayListOf()
)