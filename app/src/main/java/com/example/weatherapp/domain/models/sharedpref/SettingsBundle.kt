package com.example.weatherapp.domain.models.sharedpref

data class SettingsBundle(
    val isDarkTheme: Boolean = true,
    val tempUnit: TempUnit = TempUnit.celsius,
    val windSpeedUnit: WindSpeedUnit = WindSpeedUnit.ms,
    val precipitationUnit: PrecipitationUnit = PrecipitationUnit.mm
)
