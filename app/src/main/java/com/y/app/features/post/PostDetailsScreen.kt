package com.y.app.features.post

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.y.app.core.theme.YTheme

@Composable
fun PostDetailsScreen(postId: String) {
    Text(text = postId)
}

@Preview
@Composable
private fun PostDetailsScreenPreview() {
    YTheme {
        Surface {

        }
    }
}