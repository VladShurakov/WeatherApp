package com.example.weatherapp.feature_city_search.present.adapter.model

import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.present.adapter.CitiesAdapter

data class CityAdapterModel(
    val cityName: String,
    val cityInfo: String,
    val cityEntity: CityEntity,
    val onCityListener: CitiesAdapter.OnCityListener
)