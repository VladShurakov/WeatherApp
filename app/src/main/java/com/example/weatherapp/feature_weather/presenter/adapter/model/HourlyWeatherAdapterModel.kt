package com.example.weatherapp.feature_weather.presenter.adapter.model

import androidx.annotation.DrawableRes

data class HourlyWeatherAdapterModel(
    val time: String,
    @DrawableRes
    val weatherDrawable: Int,
    val temp: String
)