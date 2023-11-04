package com.example.weatherapp.presenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.presenter.viewmodels.WeatherViewModel

class SettingsFragment(
    private val viewModel: WeatherViewModel
) : Fragment() {
    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    companion object {
        @JvmStatic
        fun newInstance(viewModel: WeatherViewModel) = SettingsFragment(viewModel)
    }
}