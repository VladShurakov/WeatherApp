package com.example.weatherapp.util

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

sealed class WeatherType(
    val description: String,
    @DrawableRes val drawableRes: Int,
) {
    data object ClearSkySun : WeatherType(
        description = "Clear sky",
        drawableRes = R.drawable.im_clear_sun
    )

    data object ClearSkyNoon : WeatherType(
        description = "Clear sky",
        drawableRes = R.drawable.im_clear_noon
    )

    data object MainlyClearSun : WeatherType(
        description = "Mainly clear",
        drawableRes = R.drawable.im_mainly_cloudy_sun
    )

    data object MainlyClearNoon : WeatherType(
        description = "Mainly clear",
        drawableRes = R.drawable.im_mainly_cloudy_noon
    )

    data object PartlyCloudySun : WeatherType(
        description = "Partly cloudy",
        drawableRes = R.drawable.im_partly_cloudy_sun
    )

    data object PartlyCloudyNoon : WeatherType(
        description = "Partly cloudy",
        drawableRes = R.drawable.im_partly_cloudy_noon
    )

    data object Overcast : WeatherType(
        description = "Overcast",
        drawableRes = R.drawable.im_cloudy
    )

    data object Foggy : WeatherType(
        description = "Foggy",
        drawableRes = R.drawable.im_cloudy
    )

    data object DepositingRimeFog : WeatherType(
        description = "Depositing rime fog",
        drawableRes = R.drawable.im_cloudy
    )

    data object LightDrizzle : WeatherType(
        description = "Light drizzle",
        drawableRes = R.drawable.im_rainy
    )

    data object ModerateDrizzle : WeatherType(
        description = "Moderate drizzle",
        drawableRes = R.drawable.im_rainy
    )

    data object DenseDrizzle : WeatherType(
        description = "Dense drizzle",
        drawableRes = R.drawable.im_rainy
    )

    data object LightFreezingDrizzle : WeatherType(
        description = "Slight freezing drizzle",
        drawableRes = R.drawable.im_snowy
    )

    data object DenseFreezingDrizzle : WeatherType(
        description = "Dense freezing drizzle",
        drawableRes = R.drawable.im_snowy
    )

    data object SlightRain : WeatherType(
        "Slight rain",
        R.drawable.im_rainy
    )

    data object ModerateRain : WeatherType(
        "Rainy",
        R.drawable.im_rainy
    )

    data object HeavyRain : WeatherType(
        "Heavy rain",
        R.drawable.im_rainy
    )

    data object HeavyFreezingRain : WeatherType(
        "Heavy freezing rain",
        R.drawable.im_rainy
    )

    data object SlightSnowFall : WeatherType(
        "Slight snow fall",
        R.drawable.im_snowy
    )

    data object ModerateSnowFall : WeatherType(
        "Moderate snow fall",
        R.drawable.im_heavy_snow
    )

    data object HeavySnowFall : WeatherType(
        description = "Heavy snow fall",
        drawableRes = R.drawable.im_heavy_snow
    )

    data object SnowGrains : WeatherType(
        description = "Snow grains",
        drawableRes = R.drawable.im_heavy_snow
    )

    data object SlightRainShowers : WeatherType(
        description = "Slight rain showers",
        drawableRes = R.drawable.im_rainy
    )

    data object ModerateRainShowers : WeatherType(
        "Moderate rain showers",
        R.drawable.im_rainy
    )

    data object ViolentRainShowers : WeatherType(
        "Violent rain showers",
        R.drawable.im_rainy
    )

    data object SlightSnowShowers : WeatherType(
        "Light snow showers",
        R.drawable.im_snowy
    )

    data object HeavySnowShowers : WeatherType(
        "Heavy snow showers",
        R.drawable.im_heavy_snow
    )

    data object ModerateThunderstorm : WeatherType(
        "Moderate thunderstorm",
        R.drawable.im_thunder
    )

    data object SlightHailThunderstorm : WeatherType(
        "Thunderstorm with slight hail",
        R.drawable.im_rainy_thunder
    )

    data object HeavyHailThunderstorm : WeatherType(
        "Thunderstorm with heavy hail",
        R.drawable.im_rainy_thunder
    )

    companion object {
        // Convert from Weather code to WeatherType
        fun toWeatherType(weatherCode: Int, isDay: Boolean = true): WeatherType {
            return when (weatherCode) {
                0 -> when (isDay) {
                    true -> ClearSkySun
                    false -> ClearSkyNoon
                }

                1 -> when (isDay) {
                    true -> MainlyClearSun
                    false -> MainlyClearNoon
                }

                2 -> when (isDay) {
                    true -> PartlyCloudySun
                    false -> PartlyCloudyNoon
                }

                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> Overcast
            }
        }
    }
}
