package com.example.weatherapp.feature_settings.domain.model

import com.example.weatherapp.feature_settings.domain.model.weather_unit.PrecipitationUnit
import com.example.weatherapp.feature_settings.domain.model.weather_unit.TempUnit
import com.example.weatherapp.feature_settings.domain.model.weather_unit.WindSpeedUnit

data class SettingsBundle(
    val isDarkTheme: Boolean = true,
    val tempUnit: TempUnit = TempUnit.celsius,
    val windSpeedUnit: WindSpeedUnit = WindSpeedUnit.ms,
    val precipitationUnit: PrecipitationUnit = PrecipitationUnit.mm
)
