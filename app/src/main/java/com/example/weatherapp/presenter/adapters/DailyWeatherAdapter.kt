package com.example.weatherapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.domain.models.network.DailyWeather
import com.example.weatherapp.databinding.CardDailyWeatherBinding
import com.example.weatherapp.domain.util.WeatherType
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.presenter.adapters.models.DailyWeatherAdapterModel
import java.lang.Exception

class DailyWeatherAdapter : RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherHolder>(){
    private var dailyWeather: NetworkResult<DailyWeather> = NetworkResult.Success(DailyWeather())

    inner class DailyWeatherHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CardDailyWeatherBinding.bind(view)

        fun bind(dailyWeatherAdapterModel: DailyWeatherAdapterModel) = with(binding){
            tvDate.text = dailyWeatherAdapterModel.date
            imWeather.setImageResource(dailyWeatherAdapterModel.weatherDrawable)
            tvTemp.text = dailyWeatherAdapterModel.temp
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
        (dailyWeather as NetworkResult.Success<DailyWeather>).data.daily?.apply {
            val date = this.time[position]
            val weatherCode = this.weatherCode[position]
            val drawable = WeatherType.toWeatherType(weatherCode).drawableRes
            val tempMax = this.tempMax[position]
            val tempMin = this.tempMin[position]
            val temp = "${tempMax}°/${tempMin}°"
            holder.bind(
                DailyWeatherAdapterModel(
                    date = date,
                    weatherDrawable = drawable,
                    temp =temp
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return when (dailyWeather){
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> {
                (dailyWeather as NetworkResult.Success<DailyWeather>).data.daily?.weatherCode?.size ?: 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDailyWeather(dailyWeather: NetworkResult<DailyWeather>?){
        this.dailyWeather = dailyWeather ?: NetworkResult.Error(Exception())
        notifyDataSetChanged()
    }

}