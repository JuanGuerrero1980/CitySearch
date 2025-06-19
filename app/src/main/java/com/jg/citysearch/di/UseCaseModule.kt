package com.jg.citysearch.di

import com.jg.citysearch.domain.repository.CityRepository
import com.jg.citysearch.domain.usecase.DownloadCitiesUseCase
import com.jg.citysearch.domain.usecase.GetCitiesUseCase
import com.jg.citysearch.domain.usecase.GetCityByIdUseCase
import com.jg.citysearch.domain.usecase.GetPagedCitiesUseCase
import com.jg.citysearch.domain.usecase.GetWeatherByLocation
import com.jg.citysearch.domain.usecase.SetFavoriteCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDownloadCitiesUseCase(repository: CityRepository): DownloadCitiesUseCase {
        return DownloadCitiesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPagedCitiesUseCase(repository: CityRepository): GetPagedCitiesUseCase {
        return GetPagedCitiesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetFavoriteCityUseCase(repository: CityRepository): SetFavoriteCityUseCase {
        return SetFavoriteCityUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCityByIdUseCase(repository: CityRepository): GetCityByIdUseCase {
        return GetCityByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWeatherByLocationUseCase(repository: CityRepository): GetWeatherByLocation {
        return GetWeatherByLocation(repository)
    }

    @Provides
    @Singleton
    fun provideGetCitiesUseCase(repository: CityRepository): GetCitiesUseCase {
        return GetCitiesUseCase(repository)
    }
}
