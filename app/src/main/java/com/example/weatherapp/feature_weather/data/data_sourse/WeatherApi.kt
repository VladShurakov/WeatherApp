package com.example.weatherapp.feature_weather.data.data_sourse

import com.example.weatherapp.feature_weather.domain.model.CurrentWeather
import com.example.weatherapp.feature_weather.domain.model.DailyWeather
import com.example.weatherapp.feature_weather.domain.model.HourlyWeather
import retrofit2.http.GET
import retrofit2.http.Query

/*
*   API for getting current/hourly/daily weather
**/
interface WeatherApi {

    @GET("forecast?current=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation,weathercode,windspeed_10m&timezone=auto")
    suspend fun getCurrentWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0,
        @Query(value = "temperature_unit") tempUnit: String,
        @Query(value = "windspeed_unit") windSpeedUnit: String,
        @Query(value = "precipitation_unit") precipitationUnit: String
    ): CurrentWeather

    @GET("forecast?hourly=temperature_2m,weathercode&timezone=auto&forecast_days=1")
    suspend fun getHourlyWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0,
        @Query(value = "temperature_unit") tempUnit: String,
        @Query(value = "windspeed_unit") windSpeedUnit: String,
        @Query(value = "precipitation_unit") precipitationUnit: String
    ): HourlyWeather

    @GET("forecast?daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=auto")
    suspend fun getDailyWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0,
        @Query(value = "temperature_unit") tempUnit: String,
        @Query(value = "windspeed_unit") windSpeedUnit: String,
        @Query(value = "precipitation_unit") precipitationUnit: String
    ): DailyWeather
}