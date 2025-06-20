package com.jg.citysearch.domain.model

data class Weather(
    val main: String?,
    val description: String?,
    val temp: Double,
    val humidity: Int,
    val icon: String?
)