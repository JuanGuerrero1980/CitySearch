package com.jg.citysearch.presentation.home

sealed class HomeUIState {

    object Idle : HomeUIState()
    object Loading : HomeUIState()
    object Success : HomeUIState()
    data class Error(val error: Throwable?) : HomeUIState()
}