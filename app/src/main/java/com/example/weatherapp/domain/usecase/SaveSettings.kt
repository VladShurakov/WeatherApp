package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.models.sharedpref.SettingsBundle
import com.example.weatherapp.domain.repository.SettingsRepository

class SaveSettings(
    private val repository: SettingsRepository
) {

    operator fun invoke(settingsBundle: SettingsBundle){
        repository.saveSettings(settingsBundle)
    }
}