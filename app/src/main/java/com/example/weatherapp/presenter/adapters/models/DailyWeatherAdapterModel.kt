package com.example.weatherapp.presenter.adapters.models

import androidx.annotation.DrawableRes

data class DailyWeatherAdapterModel(
    val date: String,
    @DrawableRes
    val weatherDrawable: Int,
    val temp: String
)
