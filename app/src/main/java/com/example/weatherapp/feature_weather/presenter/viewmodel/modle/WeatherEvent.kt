package com.example.weatherapp.feature_weather.presenter.viewmodel.modle

import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.util.NetworkResult

sealed interface WeatherEvent {
    data class GetWeather(val cityResult: NetworkResult<CityResult>):  WeatherEvent
}