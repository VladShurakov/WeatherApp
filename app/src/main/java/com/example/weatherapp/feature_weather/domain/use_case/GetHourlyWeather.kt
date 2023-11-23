package com.example.weatherapp.feature_weather.domain.use_case

import com.example.weatherapp.feature_weather.domain.model.HourlyWeather
import com.example.weatherapp.feature_weather.domain.repository.WeatherRepository
import com.example.weatherapp.util.NetworkResult

class GetHourlyWeather(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(
        latitude: Double, longitude: Double,
        tempUnit: String, windSpeedUnit: String, precipitationUnit: String
    ): NetworkResult<HourlyWeather> {
        return repository.getHourlyWeather(
            latitude, longitude,
            tempUnit, windSpeedUnit, precipitationUnit
        )
    }
}