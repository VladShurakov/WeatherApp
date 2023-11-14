package com.example.weatherapp.feature_city_search.domain.use_case

/*
*   Use Cases for City Search fragment
**/
data class CitySearchUseCases(
    val getDatabaseCities: GetDatabaseCities,
    val getNetworkCities: GetNetworkCities,
    val getFavoriteCities: GetFavoriteCities,
    val insertCities: InsertCities,
    val updateCity: UpdateCity
)