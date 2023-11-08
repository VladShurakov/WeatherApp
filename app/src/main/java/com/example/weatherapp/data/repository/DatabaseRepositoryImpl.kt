package com.example.weatherapp.data.repository

import com.example.weatherapp.data.database.CityDao
import com.example.weatherapp.data.database.models.CityEntity
import com.example.weatherapp.domain.models.network.CityResult
import com.example.weatherapp.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
): DatabaseRepository {

    override suspend fun insertCities(cityResults: List<CityResult>) {
        val cityEntities = cityResults.map { cityResult ->
            CityEntity(
                id = cityResult.id ?: -1,
                name = cityResult.name ?: "",
                latitude = cityResult.latitude ?: 0.0,
                longitude = cityResult.longitude ?: 0.0,
                countryCode = cityResult.countryCode ?: "",
                country = cityResult.country ?: "",
                admin = cityResult.admin ?: ""
            )
        }
        return cityDao.insertCities(cityEntities)
    }

    override suspend fun getCitiesFromDB(name: String): List<CityResult> {
        return cityDao.getCities(name).map { cityEntity ->
            CityResult(
                id = cityEntity.id,
                name = cityEntity.name,
                latitude = cityEntity.latitude,
                longitude = cityEntity.longitude,
                countryCode = cityEntity.countryCode,
                country = cityEntity.country,
                admin = cityEntity.admin
            )
        }
    }

}