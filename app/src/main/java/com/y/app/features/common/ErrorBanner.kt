package com.y.app.features.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.y.app.core.theme.YTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ErrorBanner(
    errorMessage: String?,
    modifier: Modifier = Modifier,
    visibilityDelay: Long = 4000,
    alignment: Alignment = Alignment.BottomCenter
) {
    var message: String? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(errorMessage) {
        message = errorMessage
        if (errorMessage != null) {
            coroutineScope.launch {
                delay(visibilityDelay)
                message = null
            }
        }
        onDispose { }
    }
    Box(modifier.fillMaxSize(), contentAlignment = alignment) {
        AnimatedVisibility(
            visible = message != null,
            enter = slideIn(tween(300), initialOffset = { IntOffset(it.width, 0) }),
            exit = slideOut(tween(200), targetOffset = { IntOffset(-it.width, 0) }),
        ) {
            BackgroundAwareText(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        MaterialTheme.colorScheme.errorContainer,
                        shape = ShapeDefaults.Medium
                    )
                    .padding(8.dp),
                text = errorMessage ?: "Unexpected Error",
                backgroundColor = MaterialTheme.colorScheme.errorContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ErrorBannerPrev() {
    YTheme {
        Surface {
            ErrorBanner("Unknown Error. Please try again later.")
        }
    }
}