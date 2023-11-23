package com.example.weatherapp.feature_weather.domain.use_case

import com.example.weatherapp.feature_weather.domain.model.CurrentWeather
import com.example.weatherapp.feature_weather.domain.repository.WeatherRepository
import com.example.weatherapp.util.NetworkResult

class GetCurrentWeather(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double, longitude: Double,
        tempUnit: String, windSpeedUnit: String, precipitationUnit: String
    ): NetworkResult<CurrentWeather> {
        return repository.getCurrentWeather(
            latitude, longitude,
            tempUnit, windSpeedUnit, precipitationUnit
        )
    }
}