package com.example.weatherapp.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.models.network.CityGeo
import com.example.weatherapp.domain.models.network.CurrentWeather
import com.example.weatherapp.domain.models.network.DailyWeather
import com.example.weatherapp.domain.models.network.HourlyWeather
import com.example.weatherapp.domain.models.network.CityResult
import com.example.weatherapp.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.presenter.util.MainEvent
import java.lang.Exception

data class WeatherState(
    val currentWeather: NetworkResult<CurrentWeather> = NetworkResult.Error(Exception()),
    val hourlyWeather: NetworkResult<HourlyWeather> = NetworkResult.Error(Exception()),
    val dailyWeather: NetworkResult<DailyWeather> = NetworkResult.Error(Exception()),
    val cityGeo: NetworkResult<CityGeo>? = NetworkResult.Error(Exception()),
    val cityResult: NetworkResult<CityResult> = NetworkResult.Error(Exception())
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _weatherState = MutableLiveData(WeatherState())
    val weatherState: LiveData<WeatherState> = _weatherState

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetWeather -> {
                viewModelScope.launch {
                    _weatherState.value = _weatherState.value?.copy(
                        cityResult = event.cityResult
                    )
                    if (event.cityResult is NetworkResult.Success) {
                        val latitude = event.cityResult.data.latitude ?: 60.0
                        val longitude = event.cityResult.data.longitude ?: 60.0
                        val settings = useCases.getSettings.invoke()
                        val tempUnit = settings.tempUnit
                        val windSpeedUnit = settings.windSpeedUnit
                        val precipitationUnit = settings.precipitationUnit

                        val currentWeather = useCases.getCurrentWeather.invoke(
                            latitude, longitude,
                            tempUnit, windSpeedUnit, precipitationUnit
                        )
                        val hourlyWeather = useCases.getHourlyWeather.invoke(
                            latitude, longitude,
                            tempUnit, windSpeedUnit, precipitationUnit
                        )
                        val dailyWeather = useCases.getDailyWeather.invoke(
                            latitude, longitude,
                            tempUnit, windSpeedUnit, precipitationUnit
                        )
                        _weatherState.value = weatherState.value?.copy(
                            currentWeather = currentWeather,
                            hourlyWeather = hourlyWeather,
                            dailyWeather = dailyWeather,
                        )
                    }
                }
            }

            is MainEvent.GetCity -> {
                if (event.name.length >= 2) {
                    viewModelScope.launch {
                        // Get cities from database
                        val citiesFromDb = useCases.getCitiesFromDB(event.name)

                        //Database has more than 20 cities = Show from database
                        if (citiesFromDb is NetworkResult.Success && citiesFromDb.data.cityResults.count() >= 20) {
                            _weatherState.value = _weatherState.value?.copy(
                                cityGeo = citiesFromDb
                            )
                        }
                        // Database has less than 20 cities = Try to show from network
                        else {
                            // Get cities from network
                            when (val cityGeo = useCases.getCity.invoke(event.name)) {
                                is NetworkResult.Error -> {
                                    // Show cities from database
                                    _weatherState.value = weatherState.value?.copy(
                                        cityGeo = citiesFromDb
                                    )
                                }

                                is NetworkResult.Success -> {
                                    // Show cities from network
                                    _weatherState.value = weatherState.value?.copy(
                                        cityGeo = cityGeo
                                    )
                                    // Insert cities to database
                                    useCases.insertCities(cityGeo.data.cityResults)
                                }
                            }
                        }
                    }
                }else{
                    _weatherState.value = weatherState.value?.copy(
                        cityGeo = NetworkResult.Error(Exception())
                    )
                }
            }
        }
    }
}