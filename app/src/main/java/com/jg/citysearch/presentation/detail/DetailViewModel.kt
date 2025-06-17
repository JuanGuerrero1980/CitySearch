package com.jg.citysearch.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import com.jg.citysearch.domain.usecase.GetCityByIdUseCase
import com.jg.citysearch.domain.usecase.GetWeatherByLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase,
    private val getWeatherByLocation: GetWeatherByLocation,
) : ViewModel() {
    private val _state = MutableStateFlow<DetailUIState>(DetailUIState.Idle)
    val state: StateFlow<DetailUIState> = _state
    private val _weather = MutableStateFlow<Weather?>(null)
    val weather: StateFlow<Weather?> = _weather

    fun getCityById(cityId: Int) = viewModelScope.launch {
        _state.value = DetailUIState.Idle
        try {
            val city = getCityByIdUseCase(cityId)
            _state.value = DetailUIState.Success(city)
            getWeatherByCityId(city)
        } catch (e: Exception) {
            _state.value = DetailUIState.Error(e)
        }
    }

    suspend fun getWeatherByCityId(city: City) {
        try {
            val weather = getWeatherByLocation(city = city)
            _weather.value = weather
        } catch (e: Exception) {
            _weather.value = null
        }
    }

}