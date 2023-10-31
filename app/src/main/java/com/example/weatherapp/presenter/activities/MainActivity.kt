package com.example.weatherapp.presenter.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presenter.fragments.CityFragment
import com.example.weatherapp.presenter.fragments.WeatherFragment
import com.example.weatherapp.presenter.viewmodels.WeatherViewModel
import com.example.weatherapp.presenter.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        openScreen(Screen.SearchCity)
    }

    private fun openFragment(fragment: Fragment, toBackStack: Boolean){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, fragment)
            .also {
                if (toBackStack) it.addToBackStack(null)
            }
            .commit()
    }

    fun openScreen(screen: Screen){
        when (screen){
            Screen.Weather -> {
                openFragment(WeatherFragment.newInstance(weatherViewModel), true)
            }
            Screen.SearchCity -> {
                openFragment(CityFragment.newInstance(weatherViewModel), false)
            }
        }
    }
}