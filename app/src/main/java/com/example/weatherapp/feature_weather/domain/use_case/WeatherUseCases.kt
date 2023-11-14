package com.example.weatherapp.feature_weather.domain.use_case

/*
 *  Use Cases for Weather fragment
 */
data class WeatherUseCases(
    val getCurrentWeather: GetCurrentWeather,
    val getHourlyWeather: GetHourlyWeather,
    val getDailyWeather: GetDailyWeather,
)