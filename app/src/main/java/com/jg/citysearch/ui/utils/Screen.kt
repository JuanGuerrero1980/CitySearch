package com.jg.citysearch.ui.utils

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jg.citysearch.ui.theme.CitySearchTheme

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit) {
    CitySearchTheme {
        Surface(
            modifier = modifier,
            content = content
        )
    }
}