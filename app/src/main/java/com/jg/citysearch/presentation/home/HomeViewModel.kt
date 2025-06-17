package com.jg.citysearch.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.usecase.DownloadCitiesUseCase
import com.jg.citysearch.domain.usecase.GetPagedCitiesUseCase
import com.jg.citysearch.domain.usecase.SetFavoriteCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val downloadCitiesUseCase: DownloadCitiesUseCase,
    private val getPagedCitiesUseCase: GetPagedCitiesUseCase,
    private val setFavoriteCityUseCase: SetFavoriteCityUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUIState>(HomeUIState.Idle)
    val state: StateFlow<HomeUIState> = _state
    private val searchQuery = MutableStateFlow("")
    val sortByFavorite = MutableStateFlow(false)

    fun downloadCities() {
        viewModelScope.launch {
            _state.value = HomeUIState.Loading

            val result = downloadCitiesUseCase()

            _state.value = if (result.isSuccess) {
                HomeUIState.Success
            } else {
                HomeUIState.Error(result.exceptionOrNull())
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedCities: Flow<PagingData<City>> = combine(
        searchQuery, sortByFavorite
    ) { query, onlyFavorites ->
        Pair(query, onlyFavorites)
    }.flatMapLatest { (query, onlyFavorites) ->
        getPagedCitiesUseCase(query, onlyFavorites)
    }.cachedIn(viewModelScope)

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun toggleSortByFavorite() {
        sortByFavorite.value = !sortByFavorite.value
    }

    fun updateFavorite(city: City, isFavorite: Boolean
    ) = viewModelScope.launch {
        setFavoriteCityUseCase(city = city, isFavorite = isFavorite)
    }
}