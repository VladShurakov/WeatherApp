package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetDailyWeather (
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(latitude: Double, longitude: Double): NetworkResult<DailyWeather> {
        return repository.getDailyWeather(latitude, longitude)
    }
}