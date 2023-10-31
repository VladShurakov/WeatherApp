package com.example.weatherapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.weatherapp.R
import com.example.weatherapp.data.network.models.CityGeo
import com.example.weatherapp.data.network.models.CityResult
import com.example.weatherapp.databinding.CardCityBinding
import com.example.weatherapp.domain.util.NetworkResult


class CitiesAdapter(
    private val onCityListener: OnCityListener,
) : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    private var cityGeo: NetworkResult<CityGeo?> = NetworkResult.Success(CityGeo())

    inner class CityHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardCityBinding.bind(view)

        fun bind(cityResult: CityResult, onCityListener: OnCityListener) = with(binding) {
            val between = when {
                cityResult.admin.isNullOrBlank() || cityResult.country.isNullOrBlank() -> ""
                else -> ", "
            }
            val cityInfo = cityResult.admin + between + cityResult.country
            val countryCode = cityResult.countryCode?.lowercase()
            val url = "https://raw.githubusercontent.com/hampusborgos/country-flags/main/png100px/${countryCode}.png"

            tvCityName.text = cityResult.name
            tvCityInfo.text = cityInfo
            imCountry.load(url){
                crossfade(true)
                error(R.drawable.unknown)
                transformations(CircleCropTransformation())
                build()
            }
            itemView.setOnClickListener {
                onCityListener.onCityClick(NetworkResult.Success(cityResult))
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            /* resource = */ R.layout.card_city,
            /* root = */ parent,
            /* attachToRoot = */ false
        )
        return CityHolder(view = view)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(
            cityResult = (cityGeo as NetworkResult.Success<CityGeo?>).data?.cityResults?.get(position) ?: CityResult(),
            onCityListener = onCityListener
        )
    }

    override fun getItemCount(): Int {
        return when (cityGeo){
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> {
                (cityGeo as NetworkResult.Success<CityGeo?>)
                    .data?.cityResults?.size ?: 0
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGeoCity(cityGeo: NetworkResult<CityGeo?>) {
        this.cityGeo = cityGeo
        notifyDataSetChanged()
    }

    interface OnCityListener {
        fun onCityClick(cityResult: NetworkResult<CityResult>)
    }
}