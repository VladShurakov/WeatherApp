package com.example.weatherapp.feature_weather.domain.repository

import com.example.weatherapp.feature_weather.domain.model.CurrentWeather
import com.example.weatherapp.feature_weather.domain.model.DailyWeather
import com.example.weatherapp.feature_weather.domain.model.HourlyWeather
import com.example.weatherapp.feature_settings.domain.model.weather_unit.PrecipitationUnit
import com.example.weatherapp.feature_settings.domain.model.weather_unit.TempUnit
import com.example.weatherapp.feature_settings.domain.model.weather_unit.WindSpeedUnit
import com.example.weatherapp.util.NetworkResult

interface WeatherRepository {
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
}