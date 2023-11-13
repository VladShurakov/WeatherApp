package com.example.weatherapp.feature_city_search.present.viewmodel

import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity

data class CitySearchState(
    val cities: List<CityEntity> = listOf(),
    val currentCityName: String = "",
    val uiState: CitySearchUIState = CitySearchUIState.Success
)
