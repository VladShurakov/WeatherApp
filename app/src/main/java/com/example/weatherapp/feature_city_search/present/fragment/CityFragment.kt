package com.example.weatherapp.feature_city_search.present.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.feature_city_search.domain.model.CityResult
import com.example.weatherapp.util.NetworkResult
import com.example.weatherapp.MainActivity
import com.example.weatherapp.databinding.FragmentCitySearchBinding
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity
import com.example.weatherapp.feature_city_search.present.adapter.CitiesAdapter
import com.example.weatherapp.feature_city_search.present.viewmodel.CitySearchUIState
import com.example.weatherapp.util.Screen
import com.example.weatherapp.feature_city_search.present.viewmodel.CitySearchViewModel
import com.example.weatherapp.feature_weather.presenter.viewmodel.WeatherViewModel

class CityFragment : Fragment(), CitiesAdapter.OnCityListener {
    private var binding: FragmentCitySearchBinding? = null
    private val citySearchViewModel: CitySearchViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by activityViewModels()

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
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.GONE
                    }

                    CitySearchUIState.Loading -> {
                        rvCities.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                    }

                    CitySearchUIState.Empty -> {
                        rvCities.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        tvError.visibility = View.GONE
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
                }

                ibBack.setOnClickListener {
                    (activity as MainActivity?)?.openScreen(Screen.Weather)
                }

                // SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(name: String): Boolean {
                        Log.d("jkronz", "Submit")
                        citySearchViewModel.getCities(cityName = name, isTyping = false)
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        Log.d("jkronz", "Change")
                        citySearchViewModel.getCities(cityName = newText, isTyping = true)
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

    companion object {
        @JvmStatic
        fun newInstance() = CityFragment()
    }

    override fun onCityClick(cityResult: NetworkResult<CityResult>) {
        weatherViewModel.getWeather(cityResult)
        (activity as MainActivity).openScreen(Screen.Weather)
    }

    override fun onFavoriteClick(cityEntity: CityEntity) {
        citySearchViewModel.changeInFavorite(cityEntity)
        // Get cities with new data
        val cityName = citySearchViewModel.citySearchState.value?.currentCityName ?: ""
        citySearchViewModel.getCities(cityName, true)
    }
}