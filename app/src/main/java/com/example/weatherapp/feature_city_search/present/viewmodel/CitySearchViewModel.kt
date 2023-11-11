package com.example.weatherapp.feature_city_search.present.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun getCities(name: String, isTyping: Boolean = false) {
        viewModelScope.launch {
            // Cities from Database
            val databaseCities = citySearchUseCases.getDatabaseCities.invoke(name)

            // Change UI State to Loading
            _citySearchState.value = citySearchState.value?.copy(
                uiState = CitySearchUIState.Loading
            )

            if (name.length >= 2 && !isTyping) {
                // Get cities from network
                when (val cityGeo = citySearchUseCases.getNetworkCities.invoke(name)) {
                    is NetworkResult.Error -> {
                        // Show cities from database
                        when (databaseCities) {
                            is NetworkResult.Error -> {
                                _citySearchState.value = citySearchState.value?.copy(
                                    cities = databaseCities,
                                    uiState = CitySearchUIState.Error
                                )
                            }

                            is NetworkResult.Success -> {
                                _citySearchState.value = citySearchState.value?.copy(
                                    cities = databaseCities,
                                    uiState = CitySearchUIState.Success
                                )
                            }
                        }
                    }

                    is NetworkResult.Success -> {
                        // Show cities from network
                        _citySearchState.value = citySearchState.value?.copy(
                            cities = cityGeo,
                            uiState = CitySearchUIState.Success
                        )
                        // Insert cities to database
                        citySearchUseCases.insertCities(cityGeo.data.cityResults)
                    }
                }
            } else {
                _citySearchState.value = citySearchState.value?.copy(
                    cities = databaseCities,
                    uiState = CitySearchUIState.Success
                )
            }
        }
    }
}