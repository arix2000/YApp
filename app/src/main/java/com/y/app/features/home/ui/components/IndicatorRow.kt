package com.y.app.features.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorRow(
    modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable () -> Unit
) {
    val thisModifier = if (onClick != null) modifier.clickable { onClick() }
    else modifier

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .then(thisModifier)
            .padding(8.dp)
    ) {
        content()
    }
}