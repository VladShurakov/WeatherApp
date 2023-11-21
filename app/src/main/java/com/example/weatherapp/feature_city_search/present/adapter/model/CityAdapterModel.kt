package com.example.weatherapp.feature_city_search.present.adapter.model

import com.example.weatherapp.feature_city_search.domain.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_city_search.present.adapter.CitiesAdapter
import com.example.weatherapp.core.NetworkResult

/*
 *  Model for City Adapter
 */
data class CityAdapterModel(
    val cityName: String,
    val cityInfo: String,
    val cityEntity: CityEntity,
    val onCityListener: CitiesAdapter.OnCityListener,
    val cityResult: NetworkResult<CityResult>
)