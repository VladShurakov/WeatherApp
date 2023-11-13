package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository

class UpdateCity(
    private val repository: DatabaseCityRepository
) {

    suspend operator fun invoke(cityEntity: CityEntity) {
        repository.updateCity(cityEntity)
    }
}