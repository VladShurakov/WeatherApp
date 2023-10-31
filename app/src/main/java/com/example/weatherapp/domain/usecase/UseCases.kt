package com.example.weatherapp.domain.usecase

data class UseCases(
    val getCurrentWeather: GetCurrentWeather,
    val getHourlyWeather: GetHourlyWeather,
    val getDailyWeather: GetDailyWeather,
    val getGeoByCity: GetGeoByCity
)