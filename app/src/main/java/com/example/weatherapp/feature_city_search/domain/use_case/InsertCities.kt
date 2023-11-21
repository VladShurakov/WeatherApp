package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.domain.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository

/*
 *  Insert Cities to database
 */
class InsertCities(
    private val repository: DatabaseCityRepository
) {

    suspend operator fun invoke(cityEntities: List<CityEntity>) {
        repository.insertCities(cityEntities)
    }
}