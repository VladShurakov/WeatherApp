package com.example.weatherapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo("country")
    val country: String,
    @ColumnInfo("admin")
    val admin: String
)