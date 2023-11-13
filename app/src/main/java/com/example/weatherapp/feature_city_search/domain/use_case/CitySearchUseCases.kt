package com.example.weatherapp.feature_city_search.domain.use_case

data class CitySearchUseCases(
    val getDatabaseCities: GetDatabaseCities,
    val getNetworkCities: GetNetworkCities,
    val getFavoriteCities: GetFavoriteCities,
    val insertCities: InsertCities,
    val updateCity: UpdateCity
)