package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.models.network.CurrentWeather
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetCurrentWeather(
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<CurrentWeather> {
        return repository.getCurrentWeather(
            latitude, longitude,
            tempUnit, windSpeedUnit, precipitationUnit
        )
    }
}