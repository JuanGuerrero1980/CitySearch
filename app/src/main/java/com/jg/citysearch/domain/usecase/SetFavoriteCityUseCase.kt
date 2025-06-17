package com.jg.citysearch.domain.usecase

import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.repository.CityRepository

class SetFavoriteCityUseCase(
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(city: City, isFavorite: Boolean) {
        return cityRepository.setFavorite(city, isFavorite)
    }
}