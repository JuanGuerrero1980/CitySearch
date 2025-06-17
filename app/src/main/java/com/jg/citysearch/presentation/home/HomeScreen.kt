package com.jg.citysearch.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.jg.citysearch.R
import com.jg.citysearch.ui.utils.CityPagingList
import com.jg.citysearch.ui.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCityClick: (Int) -> Unit,
    onInfoClick: (Int) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val state = homeViewModel.state.collectAsState()
    val cities = homeViewModel.pagedCities.collectAsLazyPagingItems()
    val onlyFavorites =  remember { homeViewModel.sortByFavorite }.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.downloadCities()
    }
    Screen(
        modifier = modifier.fillMaxHeight()
    ) {

        Scaffold(
        ) { padding ->
            Column(
                modifier = modifier
                    .padding(padding)
                    .background(Color(0xFFF9FAFB))
            ) {

                when (state.value) {
                    is HomeUIState.Idle -> {
                        Text(stringResource(R.string.please_wait))
                    }

                    is HomeUIState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(stringResource(R.string.downloading_cities))
                        }
                    }

                    is HomeUIState.Success -> {
                        CityPagingList(
                            modifier = modifier,
                            cities = cities,
                            showOnlyFavorites = onlyFavorites.value,
                            onClick = onCityClick,
                            onQueryChange = { query ->
                                homeViewModel.setSearchQuery(query)
                            },
                            onUpdateFavorite = { city, isFav ->
                                homeViewModel.updateFavorite(city, isFav)
                            },
                            onToggleFavorites = {
                                homeViewModel.toggleSortByFavorite()
                            },
                            onInfoClick = onInfoClick)
                    }

                    is HomeUIState.Error -> {
                        val error = (state.value as HomeUIState.Error).error
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                stringResource(
                                    R.string.error_downloading,
                                    error?.localizedMessage ?: stringResource(R.string.error_unknown)
                                ))
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { homeViewModel.downloadCities() }) {
                                Text(stringResource(R.string.try_again))
                            }
                        }
                    }
                }
            }
        }
    }
}
