package com.jg.citysearch.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jg.citysearch.R
import com.jg.citysearch.domain.model.City
import com.jg.citysearch.domain.model.Weather
import com.jg.citysearch.ui.utils.LoadingCity
import com.jg.citysearch.ui.utils.Screen


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
                Column(Modifier.padding(padding)) {
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
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.City_detail, city.name),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            stringResource(R.string.country_detail, city.country),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(stringResource(R.string.lat_detail, city.latitude))
        Text(stringResource(R.string.lon_detail, city.longitude))
        Text(
            stringResource(
                R.string.fav_detail,
                if (city.isFavorite) stringResource(R.string.yes) else stringResource(
                    R.string.no
                )
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (weather != null) {
            Text(stringResource(R.string.weather_desc, weather.main ?: "N/A"))
            Text(stringResource(R.string.temp_c, weather.temp))
            Text("${stringResource(R.string.humidity, weather.humidity)}%")
        } else {
            Text(stringResource(R.string.loading_weather))
        }
    }
}
