package com.jg.citysearch.ui.utils

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WeatherIcon(icon: String, modifier: Modifier = Modifier) {
    val iconUrl = "https://openweathermap.org/img/wn/${icon}@4x.png"

    AsyncImage(
        model = iconUrl,
        contentDescription = "Weather icon",
        modifier = modifier.size(100.dp)
    )
}