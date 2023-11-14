package com.example.weatherapp.feature_settings.domain.use_case

import com.example.weatherapp.feature_settings.domain.model.SettingsBundle
import com.example.weatherapp.feature_settings.domain.repository.SettingsRepository

/*
 *  Save settings in sharedPreferences
 */
class SaveSettings(
    private val repository: SettingsRepository
) {

    operator fun invoke(settingsBundle: SettingsBundle){
        repository.saveSettings(settingsBundle)
    }
}