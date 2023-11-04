package com.example.weatherapp.domain.usecase

data class UseCases(
    val getCurrentWeather: GetCurrentWeather,
    val getHourlyWeather: GetHourlyWeather,
    val getDailyWeather: GetDailyWeather,
    val getCity: GetCity,
    val insertCities: InsertCities,
    val getCityFromDB: GetCityFromDB
)