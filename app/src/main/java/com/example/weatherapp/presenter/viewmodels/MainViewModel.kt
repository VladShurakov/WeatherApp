package com.example.weatherapp.presenter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.data.network.models.CurrentWeather
import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.data.network.models.HourlyWeather
import com.example.weatherapp.data.network.models.CityResult
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
    val cityGeo: NetworkResult<CityGeo> = NetworkResult.Error(Exception()),
    val cityResult: NetworkResult<CityResult> = NetworkResult.Error(Exception()),
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
                        val currentWeather = useCases.getCurrentWeather.invoke(latitude, longitude)
                        val hourlyWeather = useCases.getHourlyWeather.invoke(latitude, longitude)
                        val dailyWeather = useCases.getDailyWeather.invoke(latitude, longitude)
                        _weatherState.value = _weatherState.value?.copy(
                            currentWeather = currentWeather,
                            hourlyWeather = hourlyWeather,
                            dailyWeather = dailyWeather,
                            cityGeo = NetworkResult.Error(Exception()),
                        )
                    }
                }
            }

            is MainEvent.GetCity -> {
                viewModelScope.launch {
                    _weatherState.value = _weatherState.value?.copy(
                        cityGeo = useCases.getGeoByCity.invoke(event.name)
                    )
                }
            }
        }
    }
}