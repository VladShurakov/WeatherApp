package com.example.weatherapp.feature_weather.presenter.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.feature_weather.domain.model.HourlyWeather
import com.example.weatherapp.databinding.CardHourlyWeatherBinding
import com.example.weatherapp.util.NetworkResult
import com.example.weatherapp.util.WeatherType
import com.example.weatherapp.feature_weather.presenter.adapter.model.HourlyWeatherAdapterModel
import java.lang.Exception

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {
    private var hourlyWeather: NetworkResult<HourlyWeather> = NetworkResult.Success(HourlyWeather())

    inner class HourlyWeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardHourlyWeatherBinding.bind(view)
        fun bind(hourlyWeatherAdapterModel: HourlyWeatherAdapterModel) = with(binding) {
            tvTime.text = hourlyWeatherAdapterModel.time
            imWeather.setImageResource(hourlyWeatherAdapterModel.weatherDrawable)
            tvTemp.text = hourlyWeatherAdapterModel.temp
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyWeatherHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            /* resource = */ R.layout.card_hourly_weather,
            /* root = */ parent,
            /* attachToRoot = */ false
        )
        return HourlyWeatherHolder(view = view)
    }

    override fun onBindViewHolder(holder: HourlyWeatherHolder, position: Int) {
        (hourlyWeather as NetworkResult.Success<HourlyWeather>).data.hourly?.apply {
            val time = this.time[position].drop(11)
            val weatherCode = this.weatherCode[position]
            // "07:15" -> "7".toInt in 7..18
            val isDay = time.takeWhile { it != ':' }.toInt() in 7..18
            val drawable = WeatherType.toWeatherType(weatherCode, isDay).drawableRes
            val temp = "${this.temp[position]}°"
            holder.bind(
                HourlyWeatherAdapterModel(
                    time = time,
                    weatherDrawable = drawable,
                    temp = temp
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return when (hourlyWeather) {
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> {
                (hourlyWeather as NetworkResult.Success<HourlyWeather>).data.hourly?.weatherCode?.size ?: 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHourlyWeather(hourlyWeather: NetworkResult<HourlyWeather>?) {
        this.hourlyWeather = hourlyWeather ?: NetworkResult.Error(Exception())
        notifyDataSetChanged()
    }
}