package com.example.weatherapp.feature_city_search.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cityEntities: List<CityEntity>)

    @Query("SELECT * FROM city WHERE cityName LIKE :name  || '%' ORDER BY population DESC")
    suspend fun getCities(name: String): List<CityEntity>
}