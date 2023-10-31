package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.CurrentWeather
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetCurrentWeather(
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(latitude: Double, longitude: Double): NetworkResult<CurrentWeather> {
        return repository.getCurrentWeather(latitude, longitude)
    }
}