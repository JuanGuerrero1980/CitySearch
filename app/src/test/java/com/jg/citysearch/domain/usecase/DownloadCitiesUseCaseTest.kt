package com.jg.citysearch.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jg.citysearch.domain.repository.CityRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DownloadCitiesUseCaseTest {

    private lateinit var cityRepository: CityRepository
    private lateinit var downloadCitiesUseCase: DownloadCitiesUseCase

    @Before
    fun setUp() {
        cityRepository = mockk(relaxed = true)
        downloadCitiesUseCase = DownloadCitiesUseCase(cityRepository = cityRepository)
    }

    @Test
    fun `invoke does not download if already downloaded`() = runTest {
        coEvery { cityRepository.isCitiesDownloaded() } returns true

        val result = downloadCitiesUseCase()

        coVerify(exactly = 0) { cityRepository.getAndSaveCities() }
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `invoke downloads if not already downloaded`() = runTest {
        coEvery { cityRepository.isCitiesDownloaded() } returns false

        val result = downloadCitiesUseCase()

        coVerify(exactly = 1) { cityRepository.getAndSaveCities() }
        coVerify(exactly = 1) { cityRepository.setCitiesDownloaded(true) }
        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `invoke returns failure on exception`() = runTest {
        coEvery { cityRepository.isCitiesDownloaded() } returns false
        coEvery { cityRepository.getAndSaveCities() } throws RuntimeException("Network error")

        val result = downloadCitiesUseCase()

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).hasMessageThat().contains("Network error")
    }
}