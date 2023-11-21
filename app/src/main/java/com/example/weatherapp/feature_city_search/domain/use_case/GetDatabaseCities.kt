package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.domain.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository

/*
 *  Get Cities from database by city name
 */
class GetDatabaseCities(
    private val repository: DatabaseCityRepository
) {

    suspend operator fun invoke(cityName: String): List<CityEntity> {
        return repository.getCities(cityName)
    }
}