package com.jg.citysearch.presentation.home

import com.google.common.truth.Truth.assertThat
import com.jg.citysearch.domain.usecase.DownloadCitiesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var downloadCitiesUseCase: DownloadCitiesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        downloadCitiesUseCase = mockk()
        viewModel = HomeViewModel(downloadCitiesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `downloadCities emits Loading then Success`() = runTest {
        coEvery { downloadCitiesUseCase() } returns Result.success(Unit)

        viewModel.downloadCities()

        val states = mutableListOf<HomeUIState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        advanceUntilIdle()
        assertThat(states[0]).isInstanceOf(HomeUIState.Loading::class.java)
        assertThat(states[1]).isInstanceOf(HomeUIState.Success::class.java)
        job.cancel()
    }

    @Test
    fun `downloadCities emits Loading then Error`() = runTest {
        coEvery { downloadCitiesUseCase() } returns Result.failure(Exception("fail"))

        viewModel.downloadCities()

        val states = mutableListOf<HomeUIState>()
        val job = launch {
            viewModel.state.toList(states)
        }

        advanceUntilIdle()
        assertThat(states[0]).isInstanceOf(HomeUIState.Loading::class.java)
        assertThat(states[1]).isInstanceOf(HomeUIState.Error::class.java)
        job.cancel()
    }
}