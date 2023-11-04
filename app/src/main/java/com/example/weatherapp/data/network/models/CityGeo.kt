package com.example.weatherapp.data.network.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
*   Model for Geo API
**/
@Serializable
data class CityGeo (
  @SerializedName("results"           ) var cityResults          : List<CityResult> = arrayListOf()
)