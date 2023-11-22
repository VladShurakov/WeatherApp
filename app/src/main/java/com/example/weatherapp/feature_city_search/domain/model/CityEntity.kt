package com.example.weatherapp.feature_city_search.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 *  Entity for CityDatabase
 */
@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo("cityName")
    val name: String,
    @ColumnInfo("latitude")
    val latitude: Double,
    @ColumnInfo("longitude")
    val longitude: Double,
    @ColumnInfo("countryCode")
    val countryCode: String,
    @ColumnInfo("population")
    val population: Long,
    @ColumnInfo("country")
    val country: String,
    @ColumnInfo("admin")
    val admin: String,
    @ColumnInfo("inFavorite")
    var inFavorite: Boolean = false
)