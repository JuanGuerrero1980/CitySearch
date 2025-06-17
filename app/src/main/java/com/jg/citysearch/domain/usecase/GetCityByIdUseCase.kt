package com.jg.citysearch.domain.usecase

import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.repository.CityRepository

class GetCityByIdUseCase (
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(id: Int): City {
        return cityRepository.getCityById(id)
    }
}