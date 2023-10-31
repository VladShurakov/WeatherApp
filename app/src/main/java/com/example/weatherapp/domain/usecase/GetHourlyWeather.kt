package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.HourlyWeather
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetHourlyWeather (
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(latitude: Double, longitude: Double): NetworkResult<HourlyWeather> {
        return repository.getHourlyWeather(latitude, longitude)
    }
}