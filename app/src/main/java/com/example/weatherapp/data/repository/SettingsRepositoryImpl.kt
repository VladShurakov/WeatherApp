package com.example.weatherapp.data.repository

import android.content.Context
import androidx.activity.ComponentActivity
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.SettingsBundle
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.domain.repository.SettingsRepository

private const val SHARED_PREFS_NAME = "Settings"
private const val THEME_KEY = "Theme"
private const val TEMP_UNIT_KEY = "TempUnit"
private const val WIND_SPEED_UNIT_KEY = "WindSpeedUnit"
private const val PRECIPITATION_UNIT_KEY = "PrecipitationUnit"

class SettingsRepositoryImpl(context: Context): SettingsRepository {

    private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
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
                THEME_KEY, defaultSettingsBundle.isDarkTheme
            ),
            tempUnit = TempUnit.valueOf(
                sharedPreferences.getString(
                    TEMP_UNIT_KEY, defaultSettingsBundle.tempUnit.name
                ) ?: defaultSettingsBundle.tempUnit.name
            ),
            windSpeedUnit = WindSpeedUnit.valueOf(
                sharedPreferences.getString(
                    WIND_SPEED_UNIT_KEY, defaultSettingsBundle.windSpeedUnit.name
                ) ?: defaultSettingsBundle.windSpeedUnit.name
            ),
            precipitationUnit = PrecipitationUnit.valueOf(
                sharedPreferences.getString(
                    PRECIPITATION_UNIT_KEY, defaultSettingsBundle.precipitationUnit.name
                ) ?: defaultSettingsBundle.precipitationUnit.name
            )
        )
        return defaultSettingsBundle
    }

    override fun saveSettings(settingsBundle: SettingsBundle) {
        this.defaultSettingsBundle = settingsBundle
        sharedPreferences.edit().apply {
            putBoolean(THEME_KEY, defaultSettingsBundle.isDarkTheme)
            putString(TEMP_UNIT_KEY, defaultSettingsBundle.tempUnit.name)
            putString(WIND_SPEED_UNIT_KEY, defaultSettingsBundle.windSpeedUnit.name)
            putString(PRECIPITATION_UNIT_KEY, defaultSettingsBundle.precipitationUnit.name)
        }.apply()
    }

}