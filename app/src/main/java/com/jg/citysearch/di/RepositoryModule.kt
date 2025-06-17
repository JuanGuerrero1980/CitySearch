package com.jg.citysearch.di

import com.jg.citysearch.data.local.dao.CityDao
import com.jg.citysearch.data.local.datastore.DataStoreManager
import com.jg.citysearch.data.remote.CityApiService
import com.jg.citysearch.data.remote.WeatherApiService
import com.jg.citysearch.data.repository.CityRepositoryImpl
import com.jg.citysearch.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        cityApiService: CityApiService,
        cityDao: CityDao,
        dataStoreManager: DataStoreManager,
        weatherApiService: WeatherApiService,
    ): CityRepository {
        return CityRepositoryImpl(
            cityApiService = cityApiService,
            cityDao = cityDao,
            dataStoreManager = dataStoreManager,
            weatherApiService = weatherApiService,
        )
    }
}
