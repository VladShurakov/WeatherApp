package com.example.weatherapp.feature_weather.presenter.viewmodel

import com.example.weatherapp.core.NetworkResult
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_weather.domain.model.CurrentWeather
import com.example.weatherapp.feature_weather.domain.model.DailyWeather
import com.example.weatherapp.feature_weather.domain.model.HourlyWeather

data class WeatherState(
    val currentWeather: NetworkResult<CurrentWeather>? = null,
    val hourlyWeather: NetworkResult<HourlyWeather>? = null,
    val dailyWeather: NetworkResult<DailyWeather>? = null,
    val cityResult: NetworkResult<CityResult>? = null,
    val uiState: WeatherUiState = WeatherUiState.Empty
)