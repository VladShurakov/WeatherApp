package com.example.weatherapp.feature_city_search.domain.repository

import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity

interface DatabaseCityRepository {

    suspend fun getCities(name: String): List<CityEntity>

    suspend fun getFavoriteCities(): List<CityEntity>

    suspend fun insertCities(cityEntities: List<CityEntity>)

    suspend fun updateCity(cityEntity: CityEntity)
}