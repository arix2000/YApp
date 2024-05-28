package com.y.app.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.R
import com.y.app.core.theme.YTheme

@Composable
fun ConnectionLostBanner() {
    val isConnected by connectivityState()
    if (!isConnected)
        ConnectionLostBannerContent()
}

@Composable
fun ConnectionLostBannerContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.errorContainer)
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.align(Alignment.Center)) {
            Icon(imageVector = Icons.Default.WifiOff, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            BackgroundAwareText(
                text = stringResource(R.string.no_internet_connection),
                backgroundColor = MaterialTheme.colorScheme.errorContainer,
            )
        }
    }
}

@Preview
@Composable
private fun ConnectionLostBannerPreview() {
    YTheme {
        Surface {
            ConnectionLostBannerContent()
        }
    }
}