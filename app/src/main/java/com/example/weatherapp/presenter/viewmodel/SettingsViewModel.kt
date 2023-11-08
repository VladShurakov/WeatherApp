package com.example.weatherapp.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.sharedpref.SettingsBundle
import com.example.weatherapp.domain.usecase.UseCases
import com.example.weatherapp.presenter.util.SettingsEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _settingsState = MutableLiveData(SettingsBundle())
    val settingsState: LiveData<SettingsBundle> = _settingsState

    init {
        _settingsState.value = useCases.getSettings.invoke()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SaveSettings -> {
                useCases.saveSettings.invoke(event.settingsBundle)
                _settingsState.value = event.settingsBundle
            }
        }
    }
}