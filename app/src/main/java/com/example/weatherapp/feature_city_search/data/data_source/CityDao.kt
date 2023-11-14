package com.example.weatherapp.feature_city_search.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.feature_city_search.data.data_source.model.CityEntity

/*
 *  Dao for CityDatabase
 */
@Dao
interface CityDao {
    @Query("SELECT * FROM city WHERE cityName LIKE :cityName  || '%' ORDER BY population DESC")
    suspend fun getCities(cityName: String): List<CityEntity>

    @Query("SELECT * FROM city WHERE inFavorite == 1 ORDER BY population DESC")
    suspend fun getFavoriteCities(): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCities(cityEntities: List<CityEntity>)

    @Update
    suspend fun updateCity(cityEntity: CityEntity)
}