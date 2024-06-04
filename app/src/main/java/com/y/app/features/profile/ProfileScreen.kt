package com.y.app.features.profile

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.y.app.core.theme.YTheme

@Composable
fun ProfileScreen(userId: String) {
    Text(text = userId)
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    YTheme {
        Surface {
            ProfileScreen("")
        }
    }
}