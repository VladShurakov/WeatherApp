package com.example.weatherapp.feature_city_search.present.viewmodel

import com.example.weatherapp.feature_city_search.domain.model.CityGeo
import com.example.weatherapp.util.NetworkResult

data class CitySearchState(
    val cities: NetworkResult<CityGeo>? = null,
    val uiState: CitySearchUIState = CitySearchUIState.Empty
)
