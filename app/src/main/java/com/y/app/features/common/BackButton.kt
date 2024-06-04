package com.y.app.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    transparentBackground: Boolean = false,
) {
    Box(modifier = modifier
        .clip(CircleShape)
        .clickable { onClick() }
        .background(if (transparentBackground) Color.Transparent else MaterialTheme.colorScheme.primary)
        .padding(12.dp)) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = "Back",
            tint = if (transparentBackground)
                MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.background
        )
    }
}