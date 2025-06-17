package com.jg.citysearch.domain.usecase

import androidx.paging.PagingData
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

class GetPagedCitiesUseCase(
    private val cityRepository: CityRepository,
) {

    operator fun invoke(query: String = "", onlyFavorites: Boolean = false): Flow<PagingData<City>> {
        return cityRepository.getPagedCities(query, onlyFavorites)
    }
}