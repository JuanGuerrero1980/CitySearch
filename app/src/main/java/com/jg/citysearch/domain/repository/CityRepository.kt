package com.jg.citysearch.domain.repository

import androidx.paging.PagingData
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    suspend fun getAndSaveCities()
    suspend fun getCitiesCount(): Int
    suspend fun getCityById(id: Int): City
    suspend fun setCitiesDownloaded(downloaded: Boolean)
    suspend fun isCitiesDownloaded(): Boolean
    suspend fun clearDownloadFlag()
    fun getPagedCities(query: String, onlyFavorites: Boolean): Flow<PagingData<City>>
    suspend fun setFavorite(city: City, isFavorite: Boolean)
    suspend fun getWeatherByCityLocation(lat: Double, lon: Double): Weather

}
