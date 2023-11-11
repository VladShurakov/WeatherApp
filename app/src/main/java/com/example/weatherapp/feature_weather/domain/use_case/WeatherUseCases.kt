package com.example.weatherapp.feature_weather.domain.use_case

data class WeatherUseCases(
    val getCurrentWeather: GetCurrentWeather,
    val getHourlyWeather: GetHourlyWeather,
    val getDailyWeather: GetDailyWeather,
)