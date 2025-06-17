package com.jg.citysearch.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.citysearch.domain.usecase.GetCityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<MapUIState>(MapUIState.Idle)
    val state: StateFlow<MapUIState> = _state

    fun getCityById(cityId: Int) = viewModelScope.launch {
        _state.value = MapUIState.Idle
        try {
            val city = getCityByIdUseCase(cityId)
            _state.value = MapUIState.Success(city)
        } catch (e: Exception) {
            _state.value = MapUIState.Error(e)
        }
    }

}