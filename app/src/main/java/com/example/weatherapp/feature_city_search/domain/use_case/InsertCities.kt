package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository

class InsertCities(
    private val repository: DatabaseCityRepository
) {

    suspend operator fun invoke(cityResults: List<CityResult>) {
        repository.insertCities(cityResults)
    }
}