package com.example.weatherapp.presenter.adapters.models

import androidx.annotation.DrawableRes

data class HourlyWeatherAdapterModel(
    val time: String,
    @DrawableRes
    val weatherDrawable: Int,
    val temp: String
)