package com.y.app.features.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.YTheme

@Composable
fun FullScreenImageTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = { }
) {
    Row(
        modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 22.sp)
        IconButton(onClick = { onBackButtonClicked() }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Back")
        }
    }
}

@Preview
@Composable
private fun FullScreenImageTopBarPreview() {
    YTheme {
        Surface {
            FullScreenImageTopBar(title = "Some title")
        }
    }
}