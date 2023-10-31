package com.example.weatherapp.presenter.util

import com.example.weatherapp.data.network.models.CityResult
import com.example.weatherapp.domain.util.NetworkResult

sealed class MainEvent {
    data class GetWeather(val cityResult: NetworkResult<CityResult>) : MainEvent()
    data class GetCity(val name: String) : MainEvent()
}