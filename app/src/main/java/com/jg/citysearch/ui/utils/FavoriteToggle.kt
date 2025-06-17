package com.jg.citysearch.ui.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue

@Composable
fun FavoriteToggle(
    isFavorite: Boolean,
    onToggle: (Boolean) -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = onToggle
    ) {
        val icon = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star
        val tint = if (isFavorite) Color(0xFFFFC107) else Color.Gray
        val scale by animateFloatAsState(
            targetValue = if (isFavorite) 1.2f else 1f,
            animationSpec = tween(durationMillis = 200)
        )
        Icon(
            imageVector = icon,
            contentDescription = if (isFavorite) "Quitar de favoritos" else "Marcar como favorito",
            modifier = Modifier.scale(scale),
            tint = tint
        )
    }
}