package com.example.weatherapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.feature_city_search.present.fragment.CityFragment
import com.example.weatherapp.feature_settings.presenter.fragment.SettingsFragment
import com.example.weatherapp.feature_weather.presenter.fragment.WeatherFragment
import com.example.weatherapp.feature_settings.presenter.viewmodel.SettingsViewModel
import com.example.weatherapp.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            openScreen(Screen.Weather)
        }

        // Set theme from sharedpref
        settingsViewModel.settingsState.observe(this) {
            when (it.isDarkTheme) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }

                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, fragment)
            .commit()
    }

    fun openScreen(screen: Screen) {
        when (screen) {
            Screen.Weather -> {
                openFragment(WeatherFragment.newInstance())
            }

            Screen.SearchCity -> {
                openFragment(CityFragment.newInstance())
            }

            Screen.Settings -> {
                openFragment(SettingsFragment.newInstance())
            }
        }
    }
}