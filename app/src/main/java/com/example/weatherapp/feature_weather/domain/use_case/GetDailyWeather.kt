package com.example.weatherapp.feature_weather.domain.use_case

import com.example.weatherapp.feature_weather.domain.model.DailyWeather
import com.example.weatherapp.feature_weather.domain.repository.WeatherRepository
import com.example.weatherapp.util.NetworkResult

class GetDailyWeather (
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double, longitude: Double,
        tempUnit: String, windSpeedUnit: String, precipitationUnit: String
    ): NetworkResult<DailyWeather> {
        return repository.getDailyWeather(
            latitude, longitude,
            tempUnit, windSpeedUnit, precipitationUnit
        )
    }
}