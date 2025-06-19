package com.jg.citysearch.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.gson.internal.GsonBuildConfig
import com.jg.citysearch.data.local.dao.CityDao
import com.jg.citysearch.data.local.datastore.DataStoreManager
import com.jg.citysearch.data.local.entity.CityEntity
import com.jg.citysearch.data.remote.CityApiService
import com.jg.citysearch.data.remote.CityResponse
import com.jg.citysearch.data.remote.WeatherApiService
import com.jg.citysearch.data.remote.WeatherResponse
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import com.jg.citysearch.domain.repository.CityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CityRepositoryImpl (
    private val cityDao: CityDao,
    private val cityApiService: CityApiService,
    private val dataStoreManager: DataStoreManager,
    private val weatherApiService: WeatherApiService,
) : CityRepository {

    override suspend fun getAndSaveCities() {
        withContext(Dispatchers.IO) {
            val citiesResponse = cityApiService.getCities()
            val cityEntities = citiesResponse.map { it.toCityEntity() }
            cityEntities.chunked(100).forEach { chunk ->
                cityDao.insertAll(chunk)
            }
        }
    }

    override suspend fun getCitiesCount(): Int {
        return cityDao.count()
    }

    override suspend fun getCityById(id: Int): City {
        return withContext(Dispatchers.IO) {
            val cityEntity = cityDao.getCityById(id)
            cityEntity?.toDomainModel() ?: throw NoSuchElementException("City with id $id not found")
        }
    }

    override suspend fun setCitiesDownloaded(downloaded: Boolean) {
        dataStoreManager.setCitiesDownloaded(downloaded)
    }

    override suspend fun isCitiesDownloaded(): Boolean {
        return dataStoreManager.isCitiesDownloaded.first()
    }

    override suspend fun clearDownloadFlag() {
        dataStoreManager.clearDownloadFlag()
    }

    override fun getPagedCities(query: String, onlyFavorites: Boolean): Flow<PagingData<City>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { cityDao.getPagedCities(query, onlyFavorites) }
        ).flow.map { pagingData ->
            pagingData.map { cityEntity ->
                cityEntity.toDomainModel()
            }
        }
    }

    override fun getCities(
        query: String,
        onlyFavorites: Boolean
    ): List<City> = cityDao.getCities(query, onlyFavorites).map { it.toDomainModel() }

    override suspend fun setFavorite(city: City, isFavorite: Boolean) {
        cityDao.setFavorite(city.id, isFavorite)
    }

    override suspend fun getWeatherByCityLocation(
        lat: Double,
        lon: Double
    ): Weather {
        return withContext(Dispatchers.IO) {
            weatherApiService.getWeather(lat = lat, lon = lon).toDomainModel()
        }
    }
}

fun CityResponse.toCityEntity(): CityEntity =
    CityEntity(
        id = this.id,
        name = this.name,
        country = this.country,
        latitude = this.coordinates.lat,
        longitude = this.coordinates.lon,
    )

fun CityEntity.toDomainModel() = City(
    id = this.id,
    name = this.name,
    country = this.country,
    latitude = this.latitude,
    longitude = this.longitude,
    isFavorite = this.isFavorite,
)

fun WeatherResponse.toDomainModel(): Weather =
    Weather(
        main = this.weather.firstOrNull()?.main ?: "",
        temp = this.main.temp,
        humidity = this.main.humidity,
        description = this.weather.firstOrNull()?.description ?: "",
    )