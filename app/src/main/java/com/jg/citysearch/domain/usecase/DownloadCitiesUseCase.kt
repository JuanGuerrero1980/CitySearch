package com.jg.citysearch.domain.usecase

import com.jg.citysearch.domain.repository.CityRepository

class DownloadCitiesUseCase(
    private val cityRepository: CityRepository,
) {

    suspend operator fun invoke(): Result<Unit> {
        try {
            if (cityRepository.isCitiesDownloaded()) {
                return Result.success(Unit)
            }
            cityRepository.getAndSaveCities()
            cityRepository.setCitiesDownloaded(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
        return Result.success(Unit)
    }
}