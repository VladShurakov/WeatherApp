package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository

/*
 *  Get Favorite Cities from database
 */
class GetFavoriteCities(
    private val repository: DatabaseCityRepository
) {

    suspend operator fun invoke(): List<CityEntity> {
        return repository.getFavoriteCities()
    }
}