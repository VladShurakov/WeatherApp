package com.example.weatherapp.feature_city_search.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 2,
    exportSchema = false
)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}