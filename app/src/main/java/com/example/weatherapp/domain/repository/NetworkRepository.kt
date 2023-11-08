package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.network.CityGeo
import com.example.weatherapp.domain.models.network.CurrentWeather
import com.example.weatherapp.domain.models.network.DailyWeather
import com.example.weatherapp.domain.models.network.HourlyWeather
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.domain.util.NetworkResult

interface NetworkRepository {

    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<CurrentWeather>

    suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<HourlyWeather>

    suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<DailyWeather>

    suspend fun getCities(
        name: String
    ): NetworkResult<CityGeo>
}