package com.example.weatherapp.feature_settings.domain.repository

import com.example.weatherapp.feature_settings.domain.model.SettingsBundle

interface SettingsRepository {
    fun getSettings(): SettingsBundle

    fun saveSettings(settingsBundle: SettingsBundle)
}