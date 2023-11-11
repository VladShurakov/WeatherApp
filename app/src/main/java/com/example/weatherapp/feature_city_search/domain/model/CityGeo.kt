package com.example.weatherapp.feature_city_search.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/*
*   Model for Geo API
**/
@Serializable
data class CityGeo (
  @SerializedName("results"           ) var cityResults          : List<CityResult> = arrayListOf()
)