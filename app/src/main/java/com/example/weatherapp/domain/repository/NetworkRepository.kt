package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.data.network.models.CurrentWeather
import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.data.network.models.HourlyWeather
import com.example.weatherapp.domain.util.NetworkResult

interface NetworkRepository {

    suspend fun getCurrentWeather(latitude: Double, longitude: Double): NetworkResult<CurrentWeather>

    suspend fun getHourlyWeather(latitude: Double, longitude: Double): NetworkResult<HourlyWeather>

    suspend fun getDailyWeather(latitude: Double, longitude: Double): NetworkResult<DailyWeather>

    suspend fun getCity(name: String): NetworkResult<CityGeo>
}