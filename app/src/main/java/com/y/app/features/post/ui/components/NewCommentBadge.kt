package com.y.app.features.post.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.YTheme

@Composable
fun NewCommentBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            )
            .padding(vertical = 2.dp, horizontal = 20.dp)
    ) {
        Text(text = "New comment!", fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Preview
@Composable
private fun NewCommentBadgePreview() {
    YTheme {
        Surface {
            NewCommentBadge()
        }
    }
}