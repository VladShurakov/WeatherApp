package com.example.weatherapp.feature_settings.data.repository

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.weatherapp.feature_settings.domain.repository.SettingsRepository
import com.example.weatherapp.feature_settings.domain.model.SettingsBundle

class SettingsRepositoryImpl(context: Context) : SettingsRepository {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private var settingsBundle = SettingsBundle()

    override fun getSettings(): SettingsBundle {
        settingsBundle = SettingsBundle(
            isDarkTheme = sharedPreferences.getBoolean("dark_theme", true),
            tempUnit = sharedPreferences.getString("temperature", "celsius"),
            windSpeedUnit = sharedPreferences.getString("wind_speed", "ms"),
            precipitationUnit =sharedPreferences.getString("precipitation", "mm")
        )
        return settingsBundle
    }
}