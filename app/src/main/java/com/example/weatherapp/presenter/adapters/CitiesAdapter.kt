package com.example.weatherapp.presenter.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CardCityBinding
import com.example.weatherapp.domain.models.network.CityGeo
import com.example.weatherapp.domain.models.network.CityResult
import com.example.weatherapp.domain.util.NetworkResult
import com.example.weatherapp.presenter.adapters.models.CityAdapterModel


class CitiesAdapter(
    private val onCityListener: OnCityListener,
) : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {
    private var cityGeo: NetworkResult<CityGeo> = NetworkResult.Success(CityGeo())

    inner class CityHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardCityBinding.bind(view)

        fun bind(
            cityAdapterModel: CityAdapterModel,
            cityResult: CityResult,
            onCityListener: OnCityListener
        ) = with(binding) {
            tvCityName.text = cityAdapterModel.cityName
            tvCityInfo.text = cityAdapterModel.cityInfo
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
        (cityGeo as NetworkResult.Success<CityGeo>).data.cityResults[position].apply {
            val cityName = this.name ?: ""
            val between = when {
                this.admin.isNullOrBlank() || this.country.isNullOrBlank() -> ""
                else -> ", "
            }
            val cityInfo = (this.admin ?: "") + between + (this.country ?: "")
            holder.bind(
                cityAdapterModel = CityAdapterModel(
                    cityName = cityName,
                    cityInfo = cityInfo
                ),
                cityResult = this,
                onCityListener = onCityListener
            )
        }
    }

    override fun getItemCount(): Int {
        return when (cityGeo) {
            is NetworkResult.Error -> 0
            is NetworkResult.Success -> (cityGeo as NetworkResult.Success<CityGeo>).data.cityResults.size
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGeoCity(cityGeo: NetworkResult<CityGeo>?) {
        this.cityGeo = cityGeo ?: NetworkResult.Error(Exception())
        notifyDataSetChanged()
    }

    interface OnCityListener {
        fun onCityClick(cityResult: NetworkResult<CityResult>)
    }
}