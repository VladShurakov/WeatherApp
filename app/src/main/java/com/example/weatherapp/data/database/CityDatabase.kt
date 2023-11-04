package com.example.weatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.database.models.CityEntity

@Database(
    entities = [CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}