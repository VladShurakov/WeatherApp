package com.example.weatherapp.feature_city_search.present.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCitySearchBinding
import com.example.weatherapp.feature_city_search.domain.model.CityEntity
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.feature_city_search.present.adapter.CitiesAdapter
import com.example.weatherapp.feature_city_search.present.viewmodel.model.CitySearchUIState
import com.example.weatherapp.feature_city_search.present.viewmodel.CitySearchViewModel
import com.example.weatherapp.feature_weather.presenter.viewmodel.WeatherViewModel
import com.example.weatherapp.util.NetworkResult

class CityFragment : Fragment(), CitiesAdapter.OnCityListener {
    private var binding: FragmentCitySearchBinding? = null
    private val citySearchViewModel: CitySearchViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        citySearchViewModel.getCities("", true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCitySearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citySearchViewModel.citySearchState.observe(viewLifecycleOwner) { citySearchState ->
            // Adapter
            val citiesAdapter = CitiesAdapter(this).also {
                it.setCityEntities(citySearchState.cities)
            }

            binding?.apply {
                // City Search Ui State
                when (citySearchState.uiState) {
                    CitySearchUIState.Success -> {
                        rvCities.visibility = View.VISIBLE
                        tvInfo.visibility = View.GONE
                    }

                    CitySearchUIState.Loading -> {
                        tvInfo.text = getString(R.string.searching)
                        rvCities.visibility = View.GONE
                        tvInfo.visibility = View.VISIBLE
                    }

                    CitySearchUIState.NoNetworkConnection -> {
                        tvInfo.text = getString(R.string.no_network_connection)
                        rvCities.visibility = View.GONE
                        tvInfo.visibility = View.VISIBLE
                    }

                    CitySearchUIState.Empty -> {
                        tvInfo.text = getString(R.string.no_results)
                        rvCities.visibility = View.GONE
                        tvInfo.visibility = View.VISIBLE
                    }
                }

                // Recycler View
                rvCities.apply {
                    layoutManager = LinearLayoutManager(
                        context.applicationContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = citiesAdapter
                    itemAnimator = null
                }

                ibBack.setOnClickListener {
                    findNavController().navigate(R.id.navigateToWeatherFragment)
                }

                // SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(cityName: String): Boolean {
                        citySearchViewModel.getCities(newCityName = cityName, isTyping = false)
                        return true
                    }

                    override fun onQueryTextChange(cityName: String): Boolean {
                        citySearchViewModel.getCities(newCityName = cityName, isTyping = true)
                        return true
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCityClick(cityResult: NetworkResult<CityResult>) {
        weatherViewModel.getWeather(cityResult)
        findNavController().navigate(R.id.navigateToWeatherFragment)

    }

    override fun toggleFavorite(cityEntity: CityEntity) {
        citySearchViewModel.toggleFavorite(cityEntity)
    }
}