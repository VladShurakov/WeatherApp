package com.example.weatherapp.feature_city_search.present.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.feature_city_search.domain.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.use_case.CitySearchUseCases
import com.example.weatherapp.feature_city_search.present.viewmodel.model.CitySearchEvent
import com.example.weatherapp.feature_city_search.present.viewmodel.model.CitySearchState
import com.example.weatherapp.feature_city_search.present.viewmodel.model.CitySearchUIState
import com.example.weatherapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySearchViewModel @Inject constructor(
    private val citySearchUseCases: CitySearchUseCases
) : ViewModel() {

    private val _citySearchState = MutableLiveData(CitySearchState())
    val citySearchState: LiveData<CitySearchState> = _citySearchState

    init {
        getFavoriteCities()
    }

    fun onEvent(event: CitySearchEvent) {
        when (event) {
            is CitySearchEvent.GetCities -> {
                when {
                    // "Enter location" field is blank -> show favorite cities
                    event.cityName.isBlank() -> {
                        getFavoriteCities()
                    }

                    // User is typing -> show cities from database
                    event.isTyping -> {
                        getDatabaseCities(event.cityName)
                    }

                    // User click on enter -> show cities from network
                    !event.isTyping && (event.cityName.length >= 2) -> {
                        if (event.cityName != _citySearchState.value?.currentCityName) {
                            getNetworkCities(event.cityName)
                        }
                    }
                }
            }

            is CitySearchEvent.ToggleFavorite -> {
                toggleFavorite(event.cityEntity)
            }
        }
    }

    /*
     *  Get favorite cities from local database
     */
    private fun getFavoriteCities() = viewModelScope.launch {
        val favoriteCities = citySearchUseCases.getFavoriteCities.invoke()
        _citySearchState.value = citySearchState.value?.copy(
            cities = favoriteCities,
            uiState = CitySearchUIState.Success
        )
    }

    /*
     *  Get Cities from local database
     */
    private fun getDatabaseCities(cityName: String) = viewModelScope.launch {
        // Get cities from Database
        val databaseCities = citySearchUseCases.getDatabaseCities.invoke(cityName)

        // Init uiState
        val uiState = when {
            // No network and databaseCities empty
            WeatherApplication.hasNetwork() == false && databaseCities.isEmpty() -> {
                CitySearchUIState.NoNetworkConnection
            }

            else -> {
                CitySearchUIState.Success
            }
        }

        // Show cities from database, set uiState
        _citySearchState.value = citySearchState.value?.copy(
            cities = databaseCities,
            uiState = uiState
        )
    }

    /*
     *  Get Cities from network
     */
    private fun getNetworkCities(cityName: String) = viewModelScope.launch {
        // Set Loading uiState and update currentCityName
        _citySearchState.value = citySearchState.value?.copy(
            uiState = CitySearchUIState.Loading,
            currentCityName = cityName
        )

        // Get cities from network
        val networkCities = citySearchUseCases.getNetworkCities.invoke(cityName)
        var networkCityEntity: List<CityEntity> = listOf()

        if (networkCities is NetworkResult.Success) {
            // Convert CityResult to CityEntity
            networkCityEntity = networkCities.data.cityResults.map { cityResult ->
                CityEntity(
                    id = cityResult.id ?: 0,
                    name = cityResult.name ?: "",
                    latitude = cityResult.latitude ?: 0.0,
                    longitude = cityResult.longitude ?: 0.0,
                    countryCode = cityResult.countryCode ?: "",
                    population = cityResult.population ?: 0,
                    country = cityResult.country ?: "",
                    admin = cityResult.admin ?: ""
                )
            }
            // Insert cities from network to database
            citySearchUseCases.insertCities(
                networkCityEntity
            )
        }

        // Get cities from Database
        val databaseCities = citySearchUseCases.getDatabaseCities.invoke(cityName)

        // Set uiState
        val uiState = when {
            // No network and local database is empty
            databaseCities.isEmpty() && WeatherApplication.hasNetwork() == false -> {
                CitySearchUIState.NoNetworkConnection
            }

            // No results(Network results were inserted into local database)
            databaseCities.isEmpty() -> {
                CitySearchUIState.NoResults
            }

            // Everything is good
            else -> {
                CitySearchUIState.Success
            }
        }

        _citySearchState.value = citySearchState.value?.copy(
            cities = if (networkCityEntity.size >= databaseCities.size) networkCityEntity else databaseCities,
            uiState = uiState
        )
    }

    /*
     *  Changes inFavorite parameter in CityEntity to opposite.
     */
    private fun toggleFavorite(cityEntity: CityEntity) = viewModelScope.launch {
        // Update inFavorite
        citySearchUseCases.updateCity(cityEntity.copy(inFavorite = !cityEntity.inFavorite))
    }
}