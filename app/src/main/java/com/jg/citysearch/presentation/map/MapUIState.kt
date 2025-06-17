package com.jg.citysearch.presentation.map

import com.jg.citysearch.domain.model.City

sealed class MapUIState {
    object Idle : MapUIState()
    data class Success(val city: City) : MapUIState()
    data class Error(val error: Throwable?) : MapUIState()
}