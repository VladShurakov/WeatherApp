package com.example.weatherapp.data.network

import com.example.weatherapp.data.network.models.CurrentWeather
import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.data.network.models.HourlyWeather
import retrofit2.http.GET
import retrofit2.http.Query

/*
*   API for getting current/hourly/daily weather
**/
interface WeatherApi {

    @GET("forecast?current=temperature_2m,relativehumidity_2m,apparent_temperature,precipitation,weathercode,windspeed_10m&windspeed_unit=ms&timezone=auto")
    suspend fun getCurrentWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0
    ): CurrentWeather

    @GET("forecast?hourly=temperature_2m,weathercode&windspeed_unit=ms&timezone=auto&forecast_days=1")
    suspend fun getHourlyWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0
    ): HourlyWeather

    @GET("forecast?daily=weathercode,temperature_2m_max,temperature_2m_min&timezone=auto")
    suspend fun getDailyWeather(
        @Query(value = "latitude") latitude: Double = 60.0,
        @Query(value = "longitude") longitude: Double = 60.0
    ): DailyWeather
}