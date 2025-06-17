package com.jg.citysearch.presentation.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.jg.citysearch.ui.utils.LoadingCity
import com.jg.citysearch.ui.utils.MapContent
import com.jg.citysearch.ui.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    cityId: Int,
    onBack: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val state = mapViewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val title = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) {
        mapViewModel.getCityById(cityId = cityId)
    }
    Screen(modifier = modifier) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title.value) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Outlined.Close, contentDescription =
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
                        is MapUIState.Error -> {
                            LaunchedEffect(Unit) {
                                snackBarHostState.showSnackbar(
                                    message = "An error occurred!",
                                    actionLabel = "OK",
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }

                        MapUIState.Idle -> {
                            LoadingCity()
                        }
                        is MapUIState.Success -> {
                            val cityData = (state.value as MapUIState.Success).city
                            title.value = cityData.name
                            MapContent(city = cityData)
                        }
                    }
                }
            })
    }

}
