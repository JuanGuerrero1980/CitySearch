package com.jg.citysearch.data.remote

data class WeatherResponse(
    val main: MainInfo,
    val weather: List<WeatherDescription>
)

data class MainInfo(
    val temp: Double,
    val humidity: Int
)

data class WeatherDescription(
    val main: String,
    val description: String
)