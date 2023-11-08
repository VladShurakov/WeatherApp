package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.models.network.DailyWeather
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.domain.repository.NetworkRepository
import com.example.weatherapp.domain.util.NetworkResult

class GetDailyWeather (
    private val repository: NetworkRepository
) {

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<DailyWeather> {
        return repository.getDailyWeather(
            latitude, longitude,
            tempUnit, windSpeedUnit, precipitationUnit
        )
    }
}