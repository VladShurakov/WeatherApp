package com.example.weatherapp.feature_city_search.domain.repository

import com.example.weatherapp.feature_city_search.domain.model.CityResult

interface DatabaseCityRepository {

    suspend fun getCities(name: String): List<CityResult>

    suspend fun insertCities(cityResults: List<CityResult>)
}