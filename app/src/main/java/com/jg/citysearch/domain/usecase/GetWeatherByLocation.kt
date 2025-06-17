package com.jg.citysearch.domain.usecase

import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import com.jg.citysearch.domain.repository.CityRepository

class GetWeatherByLocation (
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(city: City): Weather {
        return cityRepository.getWeatherByCityLocation(lat = city.latitude, lon = city.longitude)
    }
}