package com.jg.citysearch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.usecase.GetCityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase,
) : ViewModel() {

    private val _selectedCity = MutableStateFlow<City?>(null)
    val selectedCity: StateFlow<City?> = _selectedCity
    private val _isMap = MutableStateFlow<Boolean?>(null)
    val isMap: StateFlow<Boolean?> = _isMap

    fun selectCity(id: Int?, isMap: Boolean?) = viewModelScope.launch {
        if (_selectedCity.value?.id != id || _isMap.value != isMap) {
            _selectedCity.value = null // Reset
            _isMap.value = isMap // Update map state
            id?.let {
                _selectedCity.value = getCityByIdUseCase(it)
            }
        }
    }
}