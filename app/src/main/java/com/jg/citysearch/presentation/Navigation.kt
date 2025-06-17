package com.jg.citysearch.presentation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jg.citysearch.R
import com.jg.citysearch.presentation.detail.DetailScreen
import com.jg.citysearch.presentation.home.HomeScreen
import com.jg.citysearch.presentation.map.MapScreen

@Composable
fun Navigation(
    viewModel: NavigationViewModel = hiltViewModel()
) {
    val landscape = isLandscape()
    val selectedCity = viewModel.selectedCity.collectAsState()
    val showMap = viewModel.isMap.collectAsState()

    if(landscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Master
            HomeScreen(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onCityClick = { city -> viewModel.selectCity(city, true) },
                onInfoClick = { cityId -> viewModel.selectCity(cityId, false) },
            )

            // Detail
            Box(modifier = Modifier.weight(1f)) {
                selectedCity.value?.let {
                    if (showMap.value == true){
                        MapScreen(
                            cityId = it.id,
                            onBack = { viewModel.selectCity(null, null) })
                    } else {
                        DetailScreen(
                            cityId = it.id,
                            onBack = { viewModel.selectCity(null, null) }
                        )
                    }
                } ?: SelectCity()
            }
        }

    } else {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onCityClick = { id ->
                        navController.navigate(Screen.Map.createRoute(id))
                    },
                    onInfoClick = { id ->
                        navController.navigate(Screen.Info.createRoute(id))
                    })
            }
            composable(Screen.Map.route) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull() ?: return@composable
                MapScreen(cityId = cityId, onBack = { navController.popBackStack() })
            }
            composable(Screen.Info.route) { backStackEntry ->
                val cityId = backStackEntry.arguments?.getString("cityId")?.toIntOrNull() ?: return@composable
                DetailScreen(cityId = cityId, onBack = { navController.popBackStack() })
            }
        }
    }

}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map/{cityId}") {
        fun createRoute(id: Int) = "map/$id"
    }
    object Info : Screen("info/{cityId}") {
        fun createRoute(id: Int) = "info/$id"
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

@Composable
fun SelectCity() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.select_a_city), modifier = Modifier.align(Alignment.Center))
    }
}