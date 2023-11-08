package com.example.weatherapp.presenter.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presenter.fragments.CityFragment
import com.example.weatherapp.presenter.fragments.SettingsFragment
import com.example.weatherapp.presenter.fragments.WeatherFragment
import com.example.weatherapp.presenter.util.Screen
import com.example.weatherapp.presenter.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null){
            openScreen(Screen.Weather)
        }

        // Set theme from sharedpref
        settingsViewModel.settingsState.observe(this){
            when (it.isDarkTheme){
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, fragment)
            .commit()
    }

    fun openScreen(screen: Screen){
        when (screen){
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