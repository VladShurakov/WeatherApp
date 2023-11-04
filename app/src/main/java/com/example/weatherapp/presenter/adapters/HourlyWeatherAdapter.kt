package com.example.weatherapp.presenter.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.network.models.HourlyWeather
import com.example.weatherapp.databinding.CardHourlyWeatherBinding
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.domain.util.WeatherType

class HourlyWeatherAdapter : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherHolder>() {
    private var hourlyWeather: NetworkResult<HourlyWeather?> = NetworkResult.Success(HourlyWeather())

    inner class HourlyWeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardHourlyWeatherBinding.bind(view)
        fun bind(time: String, @DrawableRes weatherDrawable: Int, temp: String) = with(binding) {
            tvTime.text = time
            imWeather.setImageResource(weatherDrawable)
            tvTemp.text = temp
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
        (hourlyWeather as NetworkResult.Success<HourlyWeather?>).apply {

            val time: String = this.data?.hourly?.time?.get(position)?.drop(11) ?: ""
            val weatherCode = this.data?.hourly?.weatherCode?.get(position) ?: 0

            val isDay = time.take(2).toInt() in 7..18 // "07:15" -> 7

            val drawable = WeatherType.toWeatherType(
                weatherCode = weatherCode,
                isDay = isDay
            ).drawableRes

            val temp = this.data?.hourly?.temp?.get(position).toString() + "°"

            holder.bind(
                time = time,
                weatherDrawable = drawable,
                temp = temp
            )
        }
    }

    override fun getItemCount(): Int {
        return when (hourlyWeather) {
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> {
                (hourlyWeather as NetworkResult.Success<HourlyWeather?>)
                    .data?.hourly?.weatherCode?.size ?: 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateHourlyWeather(hourlyWeather: NetworkResult<HourlyWeather?>) {
        this.hourlyWeather = hourlyWeather
        notifyDataSetChanged()
    }
}