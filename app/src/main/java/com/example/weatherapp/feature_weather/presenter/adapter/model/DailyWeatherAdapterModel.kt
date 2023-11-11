package com.example.weatherapp.feature_weather.presenter.adapter.model

import androidx.annotation.DrawableRes

data class DailyWeatherAdapterModel(
    val date: String,
    @DrawableRes
    val weatherDrawable: Int,
    val temp: String
)
