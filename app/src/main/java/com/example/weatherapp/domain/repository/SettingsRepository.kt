package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.SettingsBundle

interface SettingsRepository {

    fun getSettings(): SettingsBundle

    fun saveSettings(settingsBundle: SettingsBundle)
}