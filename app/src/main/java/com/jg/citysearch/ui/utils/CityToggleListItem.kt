package com.jg.citysearch.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jg.citysearch.R
import com.jg.citysearch.domain.model.City

@Composable
fun CityToggleListItem(
    modifier: Modifier = Modifier,
    city: City,
    isFavorite: Boolean,
    onFavoriteToggle: (Boolean) -> Unit,
    onInfoClick: () -> Unit,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text("${city.name}, ${city.country}")
        },
        supportingContent = {
            Text("Lat: ${city.latitude}, Lon: ${city.longitude}")
        },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FavoriteToggle(
                    isFavorite = isFavorite,
                    onToggle = onFavoriteToggle
                )
                IconButton(onClick = onInfoClick) {
                    Icon(Icons.Default.Info, contentDescription = stringResource(R.string.city_info))
                }
            }

        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
}
