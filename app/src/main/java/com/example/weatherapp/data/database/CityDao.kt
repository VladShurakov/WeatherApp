package com.example.weatherapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.database.models.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cityEntities: List<CityEntity>)

    @Query("SELECT * FROM city WHERE cityName LIKE :name  || '%'")
    suspend fun searchCity(name: String): List<CityEntity>
}