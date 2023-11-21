package com.example.weatherapp.feature_city_search.domain.use_case

import android.content.Context
import android.widget.Toast
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApplication
import com.example.weatherapp.feature_city_search.domain.model.CityGeo
import com.example.weatherapp.feature_city_search.domain.repository.NetworkCityRepository
import com.example.weatherapp.core.NetworkResult

/*
 *  Get Cities from network by city name
 */
class GetNetworkCities(
    private val repository: NetworkCityRepository,
    private val context: Context
) {

    suspend operator fun invoke(name: String): NetworkResult<CityGeo> {
        if (WeatherApplication.hasNetwork() == false){
            Toast.makeText(context,  context.getString(R.string.no_network_connection), Toast.LENGTH_SHORT).show()
        }
        return try {
            NetworkResult.Success(CityGeo(repository.getCities(name)))
        } catch(exception: Exception){
            NetworkResult.Error(exception = exception)
        }
    }
}