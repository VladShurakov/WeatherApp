package com.example.weatherapp.feature_city_search.present.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.use_case.CitySearchUseCases
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

    fun getCities(cityName: String, isTyping: Boolean) {
        viewModelScope.launch {
            // Change currentCityName
            _citySearchState.value = citySearchState.value?.copy(currentCityName = cityName)

            // Change UI State to Loading
            _citySearchState.value = citySearchState.value?.copy(
                uiState = CitySearchUIState.Loading
            )

            when {
                // When "Enter location" field is empty show favorite cities
                cityName.isEmpty() -> {
                    getFavoriteCities()
                }

                // When user is typing show cities from database
                isTyping -> {
                    // Get cities from Database
                    val databaseCities = citySearchUseCases.getDatabaseCities.invoke(cityName)

                    // Set uiState
                    val uiState = when {
                        databaseCities.isEmpty() -> {
                            CitySearchUIState.Empty
                        }

                        else -> {
                            CitySearchUIState.Success
                        }
                    }

                    // Show cities from database
                    _citySearchState.value = citySearchState.value?.copy(
                        cities = databaseCities,
                        uiState = uiState
                    )
                }

                // When use click on enter and "Enter location" field more than 1 char
                !isTyping && cityName.length >= 2 -> {
                    // Get cities from network
                    val networkCities = citySearchUseCases.getNetworkCities.invoke(cityName)

                    if (networkCities is NetworkResult.Success){
                        citySearchUseCases.insertCities(
                            networkCities.data.cityResults.map { cityResult ->
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
                        )
                    }

                    // Get cities from Database
                    val databaseCities = citySearchUseCases.getDatabaseCities.invoke(cityName)

                    val uiState = when {
                        databaseCities.isEmpty() -> {
                            CitySearchUIState.Empty
                        }

                        else -> {
                            CitySearchUIState.Success
                        }
                    }

                    _citySearchState.value = citySearchState.value?.copy(
                        cities = databaseCities,
                        uiState = uiState
                    )
                }
            }
        }
    }

    /*
     *  Get Favorite Cities
     */
    private fun getFavoriteCities() {
        viewModelScope.launch {
            val favoriteCities = citySearchUseCases.getFavoriteCities.invoke()
            _citySearchState.value = citySearchState.value?.copy(
                cities = favoriteCities,
                uiState = CitySearchUIState.Success
            )
        }
    }

    /*
     *  Changes inFavorite parameter in CityEntity to the opposite.
     */
    fun changeInFavorite(cityEntity: CityEntity) {
        viewModelScope.launch {
            // Update inFavorite
            citySearchUseCases.updateCity(cityEntity.copy(inFavorite = !cityEntity.inFavorite))
        }
    }
}