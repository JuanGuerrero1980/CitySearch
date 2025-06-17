package com.jg.citysearch.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jg.citysearch.data.local.entity.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("SELECT COUNT(*) FROM cities")
    suspend fun count(): Int

    @Query("""
        SELECT * FROM cities
        WHERE name LIKE :query || '%'
        AND (:onlyFavorites = 0 OR isFavorite = 1)
        ORDER BY name ASC, country ASC""")
    fun getPagedCities(query: String, onlyFavorites: Boolean): PagingSource<Int, CityEntity>

    @Query("UPDATE cities SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM cities WHERE id = :id")
    suspend fun getCityById(id: Int): CityEntity?
}
