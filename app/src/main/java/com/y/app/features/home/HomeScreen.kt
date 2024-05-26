package com.y.app.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import org.koin.compose.koinInject

@Composable
fun HomeScreen(navigator: Navigator = koinInject()) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = "HomeScreen")
        Button(onClick = { navigator.navigateTo(Screen.LoginScreen) }) {
            Text(text = "LoginScreen")
        }
        Button(onClick = { navigator.navigateTo(Screen.RegistrationScreen) }) {
            Text(text = "RegistrationScreen")
        }
        Button(onClick = { navigator.navigateTo(Screen.PostDetailsScreen) }) {
            Text(text = "PostDetailsScreen")
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    YTheme {
        Surface {
            HomeScreen()
        }
    }
}