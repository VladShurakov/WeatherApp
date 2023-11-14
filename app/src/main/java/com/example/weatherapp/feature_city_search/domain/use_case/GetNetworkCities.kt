package com.example.weatherapp.feature_city_search.domain.use_case

import com.example.weatherapp.feature_city_search.domain.model.CityGeo
import com.example.weatherapp.feature_city_search.domain.repository.NetworkCityRepository
import com.example.weatherapp.util.NetworkResult

/*
 *  Get Cities from network by city name
 */
class GetNetworkCities(
    private val repository: NetworkCityRepository
) {

    suspend operator fun invoke(name: String): NetworkResult<CityGeo> {
        return try {
            NetworkResult.Success(CityGeo(repository.getCities(name)))
        } catch(exception: Exception){
            NetworkResult.Error(exception = exception)
        }
    }
}