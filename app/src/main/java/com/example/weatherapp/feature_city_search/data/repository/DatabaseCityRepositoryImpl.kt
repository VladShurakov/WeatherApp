package com.example.weatherapp.feature_city_search.data.repository

import com.example.weatherapp.feature_city_search.data.data_source.CityDao
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_city_search.domain.repository.DatabaseCityRepository
import javax.inject.Inject

class DatabaseCityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : DatabaseCityRepository {

    override suspend fun getCities(name: String): List<CityResult> {
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

    override suspend fun insertCities(cityResults: List<CityResult>) {
        val cityEntities = cityResults.map { cityResult ->
            CityEntity(
                id = cityResult.id ?: -1,
                name = cityResult.name ?: "",
                latitude = cityResult.latitude ?: 0.0,
                longitude = cityResult.longitude ?: 0.0,
                countryCode = cityResult.countryCode ?: "",
                population = cityResult.population ?: 0,
                country = cityResult.country ?: "",
                admin = cityResult.admin ?: ""
            )
        }
        return cityDao.insertCities(cityEntities)
    }

}