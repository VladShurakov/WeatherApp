package com.example.weatherapp.feature_weather.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.feature_weather.presenter.adapter.DailyWeatherAdapter
import com.example.weatherapp.feature_weather.presenter.adapter.HourlyWeatherAdapter
import com.example.weatherapp.feature_weather.presenter.viewmodel.WeatherUiState
import com.example.weatherapp.feature_weather.presenter.viewmodel.WeatherViewModel
import com.example.weatherapp.core.NetworkResult
import com.example.weatherapp.core.Screen
import com.example.weatherapp.core.WeatherType

class WeatherFragment : Fragment() {
    private var binding: FragmentWeatherBinding? = null
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Binding
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            ibSettings.setOnClickListener {
                (activity as MainActivity?)?.openScreen(Screen.Settings)
            }
            ibAdd.setOnClickListener {
                (activity as MainActivity?)?.openScreen(Screen.SearchCity)
            }

            weatherViewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
                // Adapters
                val hourlyAdapter = HourlyWeatherAdapter().also {
                    it.updateHourlyWeather(hourlyWeather = weatherState.hourlyWeather)
                }
                val dailyAdapter = DailyWeatherAdapter().also {
                    it.updateDailyWeather(dailyWeather = weatherState.dailyWeather)
                }

                // Weather Ui State
                when (weatherState.uiState) {
                    WeatherUiState.Success -> {
                        llWeather.visibility = View.VISIBLE
                        tvInfo.visibility = View.GONE
                        tvCityName.visibility = View.VISIBLE
                    }

                    WeatherUiState.Error -> {
                        llWeather.visibility = View.GONE
                        tvInfo.visibility = View.VISIBLE
                        tvCityName.visibility = View.INVISIBLE
                        if (weatherState.currentWeather is NetworkResult.Error) {
                            val text =
                                "${getString(R.string.error_message)}${weatherState.currentWeather.exception}"
                            tvInfo.text = text
                        }
                    }

                    WeatherUiState.Loading -> {
                        llWeather.visibility = View.GONE
                        tvInfo.visibility = View.VISIBLE
                        tvCityName.visibility = View.INVISIBLE
                        tvInfo.text = getString(R.string.loading)
                    }

                    WeatherUiState.Empty -> {
                        llWeather.visibility = View.GONE
                        tvInfo.visibility = View.GONE
                        tvCityName.visibility = View.INVISIBLE
                    }
                }

                if (weatherState.currentWeather is NetworkResult.Success) {
                    // Units
                    val tempUnit = weatherState.currentWeather.data.currentUnits?.temp ?: ""
                    val precipitationUnit =
                        weatherState.currentWeather.data.currentUnits?.precipitation ?: ""
                    val humidityUnit =
                        weatherState.currentWeather.data.currentUnits?.humidity ?: ""
                    val windSpeedUnit =
                        weatherState.currentWeather.data.currentUnits?.windSpeed ?: ""

                    // Values
                    val time = weatherState.currentWeather.data.current?.time ?: ""
                    val weatherCode =
                        weatherState.currentWeather.data.current?.weatherCode ?: 0
                    val weatherType = WeatherType.toWeatherType(
                        weatherCode,
                        time.drop(11).takeWhile { it != ':' }.toInt() in 7..18
                    )
                    val precipitation = weatherState.currentWeather.data.current?.precipitation
                    val humidity = weatherState.currentWeather.data.current?.humidity
                    val speedText = weatherState.currentWeather.data.current?.windSpeed

                    // Texts & Image
                    val dateText = time.replace("T", " ")
                    val cityText = when (weatherState.cityResult is NetworkResult.Success) {
                        true -> weatherState.cityResult.data.name
                        false -> ""
                    }
                    val weatherImage = weatherType.drawableRes
                    val temp = "${weatherState.currentWeather.data.current?.temp ?: ""}"
                    val weatherDesc = weatherType.description
                    val precipitationText = "$precipitation$precipitationUnit"
                    val humidityText = "$humidity$humidityUnit"
                    val windSpeedText = "$speedText$windSpeedUnit"

                    // Set text & image
                    tvDate.text = dateText
                    tvCityName.text = cityText
                    imWeather.setImageResource(weatherImage)
                    tvTemp.text = temp
                    tvTempUnit.text = tempUnit
                    tvWeather.text = weatherDesc
                    tvPrecipitation.text = precipitationText
                    tvHumidity.text = humidityText
                    tvWindSpeed.text = windSpeedText

                    // Hourly Recycler View
                    rvHourlyWeather.apply {
                        layoutManager = LinearLayoutManager(
                            context.applicationContext,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = hourlyAdapter
                    }

                    // Daily Recycler View
                    rvDailyWeather.apply {
                        layoutManager = LinearLayoutManager(
                            context.applicationContext,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = dailyAdapter
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherFragment()
    }
}