package com.example.weatherapp.feature_settings.domain.use_case

import com.example.weatherapp.feature_settings.domain.model.SettingsBundle
import com.example.weatherapp.feature_settings.domain.repository.SettingsRepository

/*
 *  Get settings from sharedPreferences
 */
class GetSettings(
    private val repository: SettingsRepository
) {

    operator fun invoke(): SettingsBundle {
        return repository.getSettings()
    }
}