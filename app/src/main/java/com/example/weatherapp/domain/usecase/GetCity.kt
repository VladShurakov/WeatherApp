package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetCity(
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(name: String): NetworkResult<CityGeo> {
        return repository.getCity(name)
    }
}