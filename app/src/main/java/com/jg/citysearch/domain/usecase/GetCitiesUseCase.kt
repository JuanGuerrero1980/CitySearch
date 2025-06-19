package com.jg.citysearch.domain.usecase

import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.repository.CityRepository

class GetCitiesUseCase(
    private val cityRepository: CityRepository,
) {

    operator fun invoke(query: String = "", onlyFavorites: Boolean = false): List<City> {
        return cityRepository.getCities(query, onlyFavorites)
    }
}