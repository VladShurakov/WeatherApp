package com.example.weatherapp.domain.repository

import com.example.weatherapp.data.network.models.CityResult

interface DatabaseRepository {

    suspend fun insertCities(cityEntities: List<CityResult>)

    suspend fun getCityFromDB(name: String): List<CityResult>
}