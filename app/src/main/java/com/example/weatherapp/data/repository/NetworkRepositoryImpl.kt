package com.example.weatherapp.data.repository

import com.example.weatherapp.data.network.GeoApi
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.data.network.models.CurrentWeather
import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.data.network.models.HourlyWeather
import com.example.weatherapp.domain.repository.NetworkRepository
import javax.inject.Inject
import com.example.weatherapp.domain.util.NetworkResult

class NetworkRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geoApi: GeoApi
) : NetworkRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): NetworkResult<CurrentWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getCurrentWeather(latitude = latitude, longitude = longitude)
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double
    ): NetworkResult<HourlyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getHourlyWeather(latitude = latitude, longitude = longitude)
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double
    ): NetworkResult<DailyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getDailyWeather(latitude = latitude, longitude = longitude)
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getGeoByName(name: String): NetworkResult<CityGeo> {
        return try {
            NetworkResult.Success(
                data = geoApi.searchCity(name)
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }
}