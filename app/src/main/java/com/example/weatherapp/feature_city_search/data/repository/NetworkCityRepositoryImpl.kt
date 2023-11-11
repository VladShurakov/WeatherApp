package com.example.weatherapp.feature_city_search.data.repository

import com.example.weatherapp.feature_city_search.data.data_source.GeoApi
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_city_search.domain.repository.NetworkCityRepository
import javax.inject.Inject

class NetworkCityRepositoryImpl @Inject constructor(
    private val geoApi: GeoApi
) :  NetworkCityRepository {

    override suspend fun getCities(name: String): List<CityResult> {
        return geoApi.searchCity(name).cityResults
    }
}