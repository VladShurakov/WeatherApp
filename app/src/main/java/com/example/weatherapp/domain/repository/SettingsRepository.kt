package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.sharedpref.SettingsBundle

interface SettingsRepository {

    fun getSettings(): SettingsBundle

    fun saveSettings(settingsBundle: SettingsBundle)
}