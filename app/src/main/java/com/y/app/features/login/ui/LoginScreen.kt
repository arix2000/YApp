package com.y.app.features.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.y.app.core.navigation.Navigator
import com.y.app.core.theme.YTheme
import org.koin.compose.koinInject

@Composable
fun LoginScreen(navigator: Navigator = koinInject()) {
    Column {
        Button(onClick = { navigator.popBackStack() }) {
            Text(text = "Go back")
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    YTheme {
        Surface {

        }
    }
}