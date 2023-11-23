package com.example.weatherapp.feature_city_search.present.viewmodel.model

import com.example.weatherapp.feature_city_search.domain.model.CityEntity

sealed interface CitySearchEvent{
    data class GetCities(val cityName: String, val isTyping: Boolean): CitySearchEvent
    data class ToggleFavorite(val cityEntity: CityEntity): CitySearchEvent
}