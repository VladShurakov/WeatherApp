package com.example.weatherapp.domain.usecase

import com.example.weatherapp.data.network.models.CityResult
import com.example.weatherapp.domain.repository.DatabaseRepository

class InsertCities(
    private val repository: DatabaseRepository
) {

    suspend operator fun invoke(cityResults: List<CityResult>) {
        repository.insertCities(cityResults)
    }
}