package com.example.weatherapp.feature_settings.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.feature_settings.domain.model.SettingsBundle
import com.example.weatherapp.feature_settings.domain.use_case.SettingsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _settingsState = MutableLiveData(SettingsBundle())
    val settingsState: LiveData<SettingsBundle> = _settingsState

    init {
        _settingsState.value = settingsUseCases.getSettings.invoke()
    }

    fun saveSettings(settingsBundle: SettingsBundle) {
        settingsUseCases.saveSettings.invoke(settingsBundle)
        _settingsState.value = settingsBundle
    }
}