package com.example.weatherapp.presenter.util

import com.example.weatherapp.domain.models.sharedpref.SettingsBundle

sealed class SettingsEvent {
    data class SaveSettings(val settingsBundle: SettingsBundle): SettingsEvent()
}