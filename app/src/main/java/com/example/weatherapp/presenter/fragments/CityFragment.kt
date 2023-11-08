package com.example.weatherapp.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.domain.models.network.CityResult
import com.example.weatherapp.databinding.FragmentCityBinding
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.presenter.activity.MainActivity
import com.example.weatherapp.presenter.adapters.CitiesAdapter
import com.example.weatherapp.presenter.util.MainEvent
import com.example.weatherapp.presenter.util.Screen
import com.example.weatherapp.presenter.viewmodel.WeatherViewModel

class CityFragment: Fragment(), CitiesAdapter.OnCityListener {
    private var binding: FragmentCityBinding? = null
    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
            // Adapter
            val citiesAdapter = CitiesAdapter(this).also {
                it.updateGeoCity(cityGeo = weatherState.cityGeo)
            }

            binding?.apply {
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
                        viewModel.onEvent(MainEvent.GetCity(name = name))
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        return true
                    }
                })
            }
        }
    }

    override fun onCityClick(cityResult: NetworkResult<CityResult>) {
        viewModel.onEvent(MainEvent.GetWeather(cityResult = cityResult))
        (activity as MainActivity).openScreen(Screen.Weather)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CityFragment()
    }
}