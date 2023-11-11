package com.example.weatherapp.feature_weather.presenter.viewmodel

data class WeatherState(
    val weather: Weather = Weather(),
    val uiState: WeatherUiState = WeatherUiState.Empty
)