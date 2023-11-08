package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.models.network.CityResult
import com.example.weatherapp.domain.repository.DatabaseRepository

class InsertCities(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(cityResults: List<CityResult>) {
        repository.insertCities(cityResults)
    }
}