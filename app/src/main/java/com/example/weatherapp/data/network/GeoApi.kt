package com.example.weatherapp.data.network

import com.example.weatherapp.domain.models.network.CityGeo
import retrofit2.http.GET
import retrofit2.http.Query

/*
*   API for getting Geo by city name
**/
interface GeoApi {

    @GET("search?count=20&language=en&format=json")
    suspend fun searchCity(@Query(value = "name") name: String): CityGeo
}