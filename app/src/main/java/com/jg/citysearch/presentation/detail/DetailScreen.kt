package com.jg.citysearch.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.citysearch.R
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import com.jg.citysearch.ui.utils.LoadingCity
import com.jg.citysearch.ui.utils.Screen
import com.jg.citysearch.ui.utils.WeatherSummaryCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    cityId: Int,
    onBack: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val state = detailViewModel.state.collectAsState()
    val weather = detailViewModel.weather.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()
    val title = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) {
        detailViewModel.getCityById(cityId = cityId)
    }
    Screen(modifier = modifier) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.Outlined.Close, contentDescription =
                                    stringResource(R.string.back_button_description)
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            content = { padding ->
                Column(
                    Modifier
                        .padding(padding)
                        .verticalScroll(scrollState)
                ) {
                    when (state.value) {
                        is DetailUIState.Error -> {
                            LaunchedEffect(Unit) {
                                snackBarHostState.showSnackbar(
                                    message = "An error occurred!",
                                    actionLabel = "OK",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }

                        DetailUIState.Idle -> {
                            LoadingCity()
                        }

                        is DetailUIState.Success -> {
                            val cityData = (state.value as DetailUIState.Success).city
                            title.value = cityData.name
                            CityDetailContent(city = cityData, weather = weather.value)
                        }
                    }
                }
            })
    }

}

@Composable
fun CityDetailContent(city: City, weather: Weather? = null) {

    WeatherSummaryCard(
        name = city.name,
        location = city.country,
        temperature = (weather?.temp ?: "N/A").toString(),
        description = weather?.description ?: "N/A",
        humidity = (weather?.humidity ?: "N/A").toString(),
        icon = weather?.icon ?: "N/A",
        favorite = city.isFavorite,
        loading = weather == null,
    )

}
