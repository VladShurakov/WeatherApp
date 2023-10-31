package com.example.weatherapp.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.domain.util.WeatherType
import com.example.weatherapp.presenter.activities.MainActivity
import com.example.weatherapp.presenter.adapters.DailyWeatherAdapter
import com.example.weatherapp.presenter.adapters.HourlyWeatherAdapter
import com.example.weatherapp.presenter.util.Screen
import com.example.weatherapp.presenter.viewmodels.WeatherViewModel

class WeatherFragment(
    private val viewModel: WeatherViewModel
) : Fragment() {

    private var binding: FragmentWeatherBinding? = null

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

        // Set ToolBar
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.search -> {
                    (activity as MainActivity?)?.openScreen(Screen.SearchCity)
                    true
                }
                else -> {
                    true
                }
            }
        }

        viewModel.weatherState.observe(viewLifecycleOwner) { weatherState ->
            // Adapters
            val hourlyAdapter = HourlyWeatherAdapter().also {
                it.updateHourlyWeather(hourlyWeather = weatherState.hourlyWeather)
            }
            val dailyAdapter = DailyWeatherAdapter().also {
                it.updateDailyWeather(dailyWeather = weatherState.dailyWeather)
            }

            if (weatherState.currentWeather is NetworkResult.Success){
                // Units
                val tempUnit = weatherState.currentWeather.data.currentUnits?.temp ?: ""
                val precipitationUnit = weatherState.currentWeather.data.currentUnits?.precipitation ?: ""
                val humidityUnit = weatherState.currentWeather.data.currentUnits?.humidity ?: ""
                val windSpeedUnit = weatherState.currentWeather.data.currentUnits?.windSpeed ?: ""

                // Values
                val time = weatherState.currentWeather.data.current?.time ?: ""
                val weatherCode = weatherState.currentWeather.data.current?.weatherCode ?: 0
                val weatherType = WeatherType.toWeatherType(weatherCode)
                val apparentTemp = weatherState.currentWeather.data.current?.apparentTemp
                val precipitation = weatherState.currentWeather.data.current?.precipitation
                val humidity = weatherState.currentWeather.data.current?.humidity
                val speedText = weatherState.currentWeather.data.current?.windSpeed

                // Texts & Image
                val dateText = time.replace("T", " ")
                val cityText = when (weatherState.cityResult is NetworkResult.Success){
                    true -> weatherState.cityResult.data.name
                    false -> ""
                }
                val weatherImage = weatherType.drawableRes
                val temp = "${weatherState.currentWeather.data.current?.temp ?: ""}"
                val weatherDesc = weatherType.description
                val feelsLikeText = "Feels $apparentTemp"
                val precipitationText = "$precipitation$precipitationUnit"
                val humidityText = "$humidity$humidityUnit"
                val windSpeedText = "$speedText$windSpeedUnit"

                binding?.apply {
                    // Set text & image
                    tvDate.text = dateText
                    tvCityName.text = cityText
                    imWeather.setImageResource(weatherImage)
                    tvTemp.text = temp
                    tvTempUnit.text = tempUnit
                    tvWeather.text = weatherDesc
                    tvFeelsLike.text = feelsLikeText
                    tvPrecipitation.text = precipitationText
                    tvHumidity.text = humidityText
                    tvWindSpeed.text = windSpeedText

                    // Recycler View
                    rvHourlyWeather.apply {
                        layoutManager = LinearLayoutManager(
                            context.applicationContext,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = hourlyAdapter
                    }
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

    companion object {
        @JvmStatic
        fun newInstance(viewModel: WeatherViewModel) = WeatherFragment(viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}