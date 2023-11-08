package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.models.network.CityResult

interface DatabaseRepository {

    suspend fun insertCities(cityEntities: List<CityResult>)

    suspend fun getCitiesFromDB(name: String): List<CityResult>
}