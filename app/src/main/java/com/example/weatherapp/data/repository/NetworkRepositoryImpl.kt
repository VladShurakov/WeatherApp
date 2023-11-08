package com.example.weatherapp.data.repository

import com.example.weatherapp.data.network.GeoApi
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.models.network.CityGeo
import com.example.weatherapp.domain.models.network.CurrentWeather
import com.example.weatherapp.domain.models.network.DailyWeather
import com.example.weatherapp.domain.models.network.HourlyWeather
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.domain.repository.NetworkRepository
import javax.inject.Inject
import com.example.weatherapp.domain.util.NetworkResult

class NetworkRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val geoApi: GeoApi
) : NetworkRepository {

    override suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<CurrentWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit.name,
                    windSpeedUnit = windSpeedUnit.name,
                    precipitationUnit = precipitationUnit.name
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getHourlyWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<HourlyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getHourlyWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit.name,
                    windSpeedUnit = windSpeedUnit.name,
                    precipitationUnit = precipitationUnit.name
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getDailyWeather(
        latitude: Double,
        longitude: Double,
        tempUnit: TempUnit,
        windSpeedUnit: WindSpeedUnit,
        precipitationUnit: PrecipitationUnit
    ): NetworkResult<DailyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getDailyWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit.name,
                    windSpeedUnit = windSpeedUnit.name,
                    precipitationUnit = precipitationUnit.name
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getCities(name: String): NetworkResult<CityGeo> {
        return try {
            NetworkResult.Success(
                data = geoApi.searchCity(name)
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }
}