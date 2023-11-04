package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.domain.repository.DatabaseRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetCityFromDB(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(name: String): NetworkResult<CityGeo> {
        return try {
            NetworkResult.Success(CityGeo(repository.getCityFromDB(name)))
        } catch(exception: Exception){
            NetworkResult.Error(exception = exception)
        }
    }
}