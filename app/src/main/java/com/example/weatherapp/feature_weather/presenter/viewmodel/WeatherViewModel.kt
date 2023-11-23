package com.example.weatherapp.feature_weather.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_settings.domain.use_case.SettingsUseCases
import com.example.weatherapp.feature_weather.domain.use_case.WeatherUseCases
import com.example.weatherapp.feature_weather.presenter.viewmodel.modle.WeatherEvent
import com.example.weatherapp.util.NetworkResult
import com.example.weatherapp.feature_weather.presenter.viewmodel.modle.WeatherState
import com.example.weatherapp.feature_weather.presenter.viewmodel.modle.WeatherUiState
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

    fun onEvent(event: WeatherEvent) {
        when (event) {
            is WeatherEvent.GetWeather -> {
                if (weatherState.value?.cityResult != event.cityResult) {
                    getWeather(event.cityResult)
                }
            }
        }
    }

    private fun getWeather(cityResult: NetworkResult<CityResult>) = viewModelScope.launch {
        // Init uiState
        _weatherState.value = _weatherState.value?.copy(
            uiState = WeatherUiState.Loading
        )

        when (cityResult) {
            is NetworkResult.Success -> {
                //Geo
                val latitude = cityResult.data.latitude ?: 60.0
                val longitude = cityResult.data.longitude ?: 60.0
                // Settings for weather
                val settings = settingsUseCases.getSettings.invoke()
                val tempUnit = settings.tempUnit ?: "celsius"
                val windSpeedUnit = settings.windSpeedUnit ?: "ms"
                val precipitationUnit = settings.precipitationUnit ?: "mm"

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

                when (currentWeather) {
                    is NetworkResult.Success -> {
                        _weatherState.value = weatherState.value?.copy(
                            currentWeather = currentWeather,
                            hourlyWeather = hourlyWeather,
                            dailyWeather = dailyWeather,
                            cityResult = cityResult,
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