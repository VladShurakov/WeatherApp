package com.example.weatherapp.feature_settings.domain.model

data class SettingsBundle(
    val isDarkTheme: Boolean = true,
    val tempUnit: String? = "celsius",
    val windSpeedUnit: String? = "ms",
    val precipitationUnit: String? = "mm"
)
