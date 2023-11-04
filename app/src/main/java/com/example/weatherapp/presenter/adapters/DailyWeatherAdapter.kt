package com.example.weatherapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.network.models.DailyWeather
import com.example.weatherapp.databinding.CardDailyWeatherBinding
import com.example.weatherapp.domain.util.WeatherType
import com.example.weatherapp.domain.util.NetworkResult

class DailyWeatherAdapter : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder>(){

    private var dailyWeather: NetworkResult<DailyWeather?> = NetworkResult.Success(DailyWeather())

    inner class DailyWeatherHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardDailyWeatherBinding.bind(view)

        fun bind(date: String, @DrawableRes weatherDrawable: Int, temp: String) = with(binding){
            tvDate.text = date
            imWeather.setImageResource(weatherDrawable)
            tvTemp.text = temp
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DailyWeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            /* resource = */ R.layout.card_daily_weather,
            /* root = */ parent,
            /* attachToRoot = */ false
        )
        return DailyWeatherHolder(view = view)
    }

    override fun onBindViewHolder(holder: DailyWeatherHolder, position: Int) {
        (dailyWeather as NetworkResult.Success<DailyWeather?>).apply {
            val date = this.data?.daily?.time?.get(position) ?: ""
            val weatherCode = this.data?.daily?.weatherCode?.get(position) ?: 1
            val drawable = WeatherType.toWeatherType(weatherCode).drawableRes
            val tempMax = this.data?.daily?.tempMax?.get(position)
            val tempMin = this.data?.daily?.tempMin?.get(position)
            val temp = "${tempMax}°/${tempMin}°"
            holder.bind(
                date = date,
                weatherDrawable = drawable,
                temp =temp
            )
        }
    }

    override fun getItemCount(): Int {
        return when (dailyWeather){
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> {
                (dailyWeather as NetworkResult.Success<DailyWeather?>)
                    .data?.daily?.weatherCode?.size ?: 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDailyWeather(dailyWeather: NetworkResult<DailyWeather?>){
        this.dailyWeather = dailyWeather
        notifyDataSetChanged()
    }

}