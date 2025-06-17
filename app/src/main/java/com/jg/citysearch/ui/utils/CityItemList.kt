package com.jg.citysearch.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jg.citysearch.domain.model.City

@Composable
fun CityListItem(
    city: City,
    onFavoriteToggle: (Boolean) -> Unit,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick(city.id) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${city.name}, ${city.country}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Lat: ${city.latitude}, Lng: ${city.longitude}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Switch(
            checked = city.isFavorite,
            onCheckedChange = onFavoriteToggle
        )
    }
}
