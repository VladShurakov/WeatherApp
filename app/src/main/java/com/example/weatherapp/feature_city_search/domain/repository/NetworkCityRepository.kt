package com.example.weatherapp.feature_city_search.domain.repository

import com.example.weatherapp.feature_city_search.domain.model.CityResult

interface NetworkCityRepository {

    suspend fun getCities(name: String): List<CityResult>
}