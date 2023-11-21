package com.example.weatherapp.feature_city_search.present.viewmodel.model

import com.example.weatherapp.feature_city_search.domain.model.CityEntity

data class CitySearchState(
    val cities: List<CityEntity> = listOf(),
    val currentCityName: String = "",
    val uiState: CitySearchUIState = CitySearchUIState.Success
)
