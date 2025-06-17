package com.jg.citysearch.presentation.detail

import com.jg.citysearch.domain.model.City

sealed class DetailUIState {
    object Idle : DetailUIState()
    data class Success(val city: City) : DetailUIState()
    data class Error(val error: Throwable?) : DetailUIState()
}