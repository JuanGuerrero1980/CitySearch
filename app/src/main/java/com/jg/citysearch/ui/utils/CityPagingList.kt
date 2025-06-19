package com.jg.citysearch.ui.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.jg.citysearch.R
import com.jg.citysearch.domain.model.City
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityPagingList(
    modifier: Modifier = Modifier,
    cities: LazyPagingItems<City>,
    showOnlyFavorites: Boolean,
    onClick: (Int) -> Unit,
    onInfoClick: (Int) -> Unit,
    onQueryChange: (String) -> Unit,
    onUpdateFavorite: (City, Boolean) -> Unit,
    onToggleFavorites: (Boolean) -> Unit
) {
    var search by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 5 }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = search,
            onValueChange = {
                search = it
                onQueryChange(it)
            },
            label = { Text(stringResource(R.string.search_city)) },
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .wrapContentHeight()
                .testTag("searchField"),
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.favorites), style = MaterialTheme.typography.labelSmall)
            Switch(
                checked = showOnlyFavorites,
                onCheckedChange = onToggleFavorites
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .padding(top = 8.dp),
            state = listState,
        ) {
            items(count = cities.itemCount) { index ->
                val city = cities[index]
                if (city != null) {
                    CityToggleListItem(
                        modifier = Modifier
                            .animateItem(),
                        city = city,
                        isFavorite = city.isFavorite,
                        onFavoriteToggle = { isFav ->
                            onUpdateFavorite(city, isFav)
                        },
                        onClick = { onClick(city.id) },
                        onInfoClick = { onInfoClick(city.id) }
                    )
                }
            }
            when (cities.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                is LoadState.Error -> {
                    val e = (cities.loadState.append as LoadState.Error).error
                    item {
                        Text(stringResource(R.string.error_loading_more_cities, e.localizedMessage))
                    }
                }

                else -> Unit
            }
        }
        val isListEmpty = cities.itemCount == 0 &&
                cities.loadState.refresh is LoadState.NotLoading &&
                cities.loadState.append is LoadState.NotLoading

        if (isListEmpty) {
            Text(
                text = stringResource(R.string.no_cities_found),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }


        if (showFab) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Back to top")
            }
        }
    }
}
