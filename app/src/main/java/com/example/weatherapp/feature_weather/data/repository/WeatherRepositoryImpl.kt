package com.example.weatherapp.feature_weather.data.repository

import com.example.weatherapp.feature_weather.data.data_sourse.WeatherApi
import com.example.weatherapp.feature_weather.domain.model.CurrentWeather
import com.example.weatherapp.feature_weather.domain.model.DailyWeather
import com.example.weatherapp.feature_weather.domain.model.HourlyWeather
import com.example.weatherapp.feature_weather.domain.repository.WeatherRepository
import com.example.weatherapp.util.NetworkResult
import javax.inject.Inject

/*
 * Repository for getting weather by city from network
 */
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getCurrentWeather(
        latitude: Double, longitude: Double,
        tempUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): NetworkResult<CurrentWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit,
                    windSpeedUnit = windSpeedUnit,
                    precipitationUnit = precipitationUnit
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getHourlyWeather(
        latitude: Double, longitude: Double,
        tempUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): NetworkResult<HourlyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getHourlyWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit,
                    windSpeedUnit = windSpeedUnit,
                    precipitationUnit = precipitationUnit
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }

    override suspend fun getDailyWeather(
        latitude: Double, longitude: Double,
        tempUnit: String,
        windSpeedUnit: String,
        precipitationUnit: String
    ): NetworkResult<DailyWeather> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getDailyWeather(
                    latitude = latitude,
                    longitude = longitude,
                    tempUnit = tempUnit,
                    windSpeedUnit = windSpeedUnit,
                    precipitationUnit = precipitationUnit
                )
            )
        } catch (exception: Exception) {
            NetworkResult.Error(exception = exception)
        }
    }
}