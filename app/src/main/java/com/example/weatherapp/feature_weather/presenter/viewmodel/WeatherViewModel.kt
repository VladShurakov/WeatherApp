package com.example.weatherapp.feature_weather.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_settings.domain.use_case.SettingsUseCases
import com.example.weatherapp.feature_weather.domain.use_case.WeatherUseCases
import com.example.weatherapp.core.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _weatherState = MutableLiveData(WeatherState())
    val weatherState: LiveData<WeatherState> = _weatherState

    fun getWeather(cityResult: NetworkResult<CityResult>) = viewModelScope.launch {
        val uiState = when (weatherState.value?.cityResult) {
            // cityResult(city) same
            cityResult -> WeatherUiState.Success
            // cityResult(city) new
            else -> WeatherUiState.Loading
        }
        // Init uiState
        _weatherState.value = _weatherState.value?.copy(
            uiState = uiState
        )

        when (cityResult) {
            is NetworkResult.Success -> {
                val latitude = cityResult.data.latitude ?: 60.0
                val longitude = cityResult.data.longitude ?: 60.0
                val settings = settingsUseCases.getSettings.invoke()
                val tempUnit = settings.tempUnit
                val windSpeedUnit = settings.windSpeedUnit
                val precipitationUnit = settings.precipitationUnit

                val currentWeather = weatherUseCases.getCurrentWeather.invoke(
                    latitude, longitude,
                    tempUnit, windSpeedUnit, precipitationUnit
                )
                val hourlyWeather = weatherUseCases.getHourlyWeather.invoke(
                    latitude, longitude,
                    tempUnit, windSpeedUnit, precipitationUnit
                )
                val dailyWeather = weatherUseCases.getDailyWeather.invoke(
                    latitude, longitude,
                    tempUnit, windSpeedUnit, precipitationUnit
                )

                _weatherState.value = WeatherState(
                    currentWeather,
                    hourlyWeather,
                    dailyWeather,
                    cityResult
                )

                when (currentWeather) {
                    is NetworkResult.Success -> {
                        _weatherState.value = weatherState.value?.copy(
                            uiState = WeatherUiState.Success
                        )
                    }

                    is NetworkResult.Error -> {
                        _weatherState.value = weatherState.value?.copy(
                            uiState = WeatherUiState.Error
                        )
                    }
                }
            }

            is NetworkResult.Error -> {
                _weatherState.value = weatherState.value?.copy(
                    uiState = WeatherUiState.Error
                )
            }
        }
    }
}