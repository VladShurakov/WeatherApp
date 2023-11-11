package com.example.weatherapp.feature_settings.data.repository

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.weatherapp.feature_settings.domain.repository.SettingsRepository
import com.example.weatherapp.feature_settings.domain.model.weather_unit.PrecipitationUnit
import com.example.weatherapp.feature_settings.domain.model.SettingsBundle
import com.example.weatherapp.feature_settings.domain.model.weather_unit.TempUnit
import com.example.weatherapp.feature_settings.domain.model.weather_unit.WindSpeedUnit

class SettingsRepositoryImpl(context: Context) : SettingsRepository {

    private val sharedPreferences = context.getSharedPreferences(
        Constants.SHARED_PREFS_NAME,
        ComponentActivity.MODE_PRIVATE
    )
    private var defaultSettingsBundle = SettingsBundle(
        isDarkTheme = true,
        tempUnit = TempUnit.celsius,
        windSpeedUnit = WindSpeedUnit.kmh,
        precipitationUnit = PrecipitationUnit.mm
    )

    override fun getSettings(): SettingsBundle {
        defaultSettingsBundle = SettingsBundle(
            isDarkTheme = sharedPreferences.getBoolean(
                Constants.THEME_KEY, defaultSettingsBundle.isDarkTheme
            ),
            tempUnit = TempUnit.valueOf(
                sharedPreferences.getString(
                    Constants.TEMP_UNIT_KEY, defaultSettingsBundle.tempUnit.name
                ) ?: defaultSettingsBundle.tempUnit.name
            ),
            windSpeedUnit = WindSpeedUnit.valueOf(
                sharedPreferences.getString(
                    Constants.WIND_SPEED_UNIT_KEY, defaultSettingsBundle.windSpeedUnit.name
                ) ?: defaultSettingsBundle.windSpeedUnit.name
            ),
            precipitationUnit = PrecipitationUnit.valueOf(
                sharedPreferences.getString(
                    Constants.PRECIPITATION_UNIT_KEY, defaultSettingsBundle.precipitationUnit.name
                ) ?: defaultSettingsBundle.precipitationUnit.name
            )
        )
        return defaultSettingsBundle
    }

    override fun saveSettings(settingsBundle: SettingsBundle) {
        this.defaultSettingsBundle = settingsBundle
        sharedPreferences.edit().apply {
            putBoolean(Constants.THEME_KEY, defaultSettingsBundle.isDarkTheme)
            putString(Constants.TEMP_UNIT_KEY, defaultSettingsBundle.tempUnit.name)
            putString(Constants.WIND_SPEED_UNIT_KEY, defaultSettingsBundle.windSpeedUnit.name)
            putString(
                Constants.PRECIPITATION_UNIT_KEY,
                defaultSettingsBundle.precipitationUnit.name
            )
        }.apply()
    }

    private object Constants {
        const val SHARED_PREFS_NAME = "Settings"
        const val THEME_KEY = "Theme"
        const val TEMP_UNIT_KEY = "TempUnit"
        const val WIND_SPEED_UNIT_KEY = "WindSpeedUnit"
        const val PRECIPITATION_UNIT_KEY = "PrecipitationUnit"
    }

}