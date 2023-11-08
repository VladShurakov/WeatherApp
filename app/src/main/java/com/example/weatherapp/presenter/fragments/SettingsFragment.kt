package com.example.weatherapp.presenter.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.domain.models.sharedpref.PrecipitationUnit
import com.example.weatherapp.domain.models.sharedpref.SettingsBundle
import com.example.weatherapp.domain.models.sharedpref.TempUnit
import com.example.weatherapp.domain.models.sharedpref.WindSpeedUnit
import com.example.weatherapp.presenter.activity.MainActivity
import com.example.weatherapp.presenter.util.Screen
import com.example.weatherapp.presenter.util.SettingsEvent
import com.example.weatherapp.presenter.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {
    private var binding: FragmentSettingsBinding? = null
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding?.swTheme?.isChecked = viewModel.settingsState.value?.isDarkTheme ?: false
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            // Back button
            ibBack.setOnClickListener {
                (activity as MainActivity?)?.openScreen(Screen.Weather)
            }

            // Init Dark Theme
            swTheme.isChecked = viewModel.settingsState.value?.isDarkTheme ?: true

            // Init Temp Unit
            tvTempUnit.text = context?.resources?.getStringArray(R.array.temp_units)?.get(
                viewModel.settingsState.value?.tempUnit?.ordinal ?: 0
            )

            // Init Wind Speed Unit
            tvWindSpeedUnit.text =
                context?.resources?.getStringArray(R.array.wind_speed_units)?.get(
                    viewModel.settingsState.value?.windSpeedUnit?.ordinal ?: 0
                )

            // Init Precipitation Unit
            tvPrecipitationUnit.text =
                context?.resources?.getStringArray(R.array.precipitation_units)?.get(
                    viewModel.settingsState.value?.precipitationUnit?.ordinal ?: 0
                )

            // Switch theme click listener
            swTheme.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> {
                        // Set Light theme
                        viewModel.onEvent(
                            SettingsEvent.SaveSettings(
                                viewModel.settingsState.value?.copy(isDarkTheme = true)
                                    ?: SettingsBundle()
                            )
                        )
                    }

                    false -> {
                        // Set Light theme
                        viewModel.onEvent(
                            SettingsEvent.SaveSettings(
                                viewModel.settingsState.value?.copy(isDarkTheme = false)
                                    ?: SettingsBundle()
                            )
                        )
                    }
                }
            }

            // Temp Unit Alert Dialog
            tempUnit.setOnClickListener {
                createAlertDialog(
                    R.array.temp_units,
                    R.string.temp_unit,
                    viewModel.settingsState.value?.tempUnit?.ordinal ?: 0,
                    tvTempUnit,
                    tempUnit = true
                )
            }

            // Temp Unit Alert Dialog
            windSpeedUnit.setOnClickListener {
                createAlertDialog(
                    R.array.wind_speed_units,
                    R.string.wind_speed_unit,
                    viewModel.settingsState.value?.windSpeedUnit?.ordinal ?: 0,
                    tvWindSpeedUnit,
                    windSpeedUnit = true
                )
            }

            // Temp Unit Alert Dialog
            precipitationUnit.setOnClickListener {
                createAlertDialog(
                    R.array.precipitation_units,
                    R.string.precipitation_unit,
                    viewModel.settingsState.value?.precipitationUnit?.ordinal ?: 0,
                    tvPrecipitationUnit,
                    precipitationUnit = true
                )
            }
        }
    }

    private fun createAlertDialog(
        stringArray: Int,
        title: Int,
        checkedItem: Int,
        textView: TextView,
        tempUnit: Boolean = false,
        windSpeedUnit: Boolean = false,
        precipitationUnit: Boolean = false
    ) {
        val unit = context?.resources?.getStringArray(stringArray)
        return AlertDialog.Builder(context)
            .setTitle(title)
            .setSingleChoiceItems(
                unit,
                checkedItem
            ) { dialog, which ->
                textView.text = unit?.get(which)
                when {
                    tempUnit -> {
                        viewModel.onEvent(
                            SettingsEvent.SaveSettings(
                                viewModel.settingsState.value?.copy(
                                    tempUnit = TempUnit.values()[which]
                                ) ?: SettingsBundle()
                            )
                        )
                    }

                    windSpeedUnit -> {
                        viewModel.onEvent(
                            SettingsEvent.SaveSettings(
                                viewModel.settingsState.value?.copy(
                                    windSpeedUnit = WindSpeedUnit.values()[which]
                                ) ?: SettingsBundle()
                            )
                        )
                    }

                    precipitationUnit -> {
                        viewModel.onEvent(
                            SettingsEvent.SaveSettings(
                                viewModel.settingsState.value?.copy(
                                    precipitationUnit = PrecipitationUnit.values()[which]
                                ) ?: SettingsBundle()
                            )
                        )
                    }
                }
                dialog.dismiss()
            }
            .setNeutralButton(R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}