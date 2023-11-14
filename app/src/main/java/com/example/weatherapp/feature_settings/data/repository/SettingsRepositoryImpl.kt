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
    private var settingsBundle = SettingsBundle(
        isDarkTheme = true,
        tempUnit = TempUnit.celsius,
        windSpeedUnit = WindSpeedUnit.kmh,
        precipitationUnit = PrecipitationUnit.mm
    )

    override fun getSettings(): SettingsBundle {
        settingsBundle = SettingsBundle(
            isDarkTheme = sharedPreferences.getBoolean(
                Constants.THEME_KEY, settingsBundle.isDarkTheme
            ),
            tempUnit = TempUnit.valueOf(
                sharedPreferences.getString(
                    Constants.TEMP_UNIT_KEY, settingsBundle.tempUnit.name
                ) ?: settingsBundle.tempUnit.name
            ),
            windSpeedUnit = WindSpeedUnit.valueOf(
                sharedPreferences.getString(
                    Constants.WIND_SPEED_UNIT_KEY, settingsBundle.windSpeedUnit.name
                ) ?: settingsBundle.windSpeedUnit.name
            ),
            precipitationUnit = PrecipitationUnit.valueOf(
                sharedPreferences.getString(
                    Constants.PRECIPITATION_UNIT_KEY, settingsBundle.precipitationUnit.name
                ) ?: settingsBundle.precipitationUnit.name
            )
        )
        return settingsBundle
    }

    override fun saveSettings(settingsBundle: SettingsBundle) {
        this.settingsBundle = settingsBundle
        sharedPreferences.edit().apply {
            putBoolean(Constants.THEME_KEY, settingsBundle.isDarkTheme)
            putString(Constants.TEMP_UNIT_KEY, settingsBundle.tempUnit.name)
            putString(Constants.WIND_SPEED_UNIT_KEY, settingsBundle.windSpeedUnit.name)
            putString(Constants.PRECIPITATION_UNIT_KEY, settingsBundle.precipitationUnit.name)
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