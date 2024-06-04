package com.y.app.features.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.YTheme
import com.y.app.features.registration.data.ProfileColorEnum

@Composable
fun Avatar(firstName: String, avatarColor: ProfileColorEnum, avatarSize: Dp, fontSize: TextUnit, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier
            .border(
                width = 3.dp,
                color = lerp(avatarColor.color, MaterialTheme.colorScheme.background, 0.3f),
                shape = CircleShape
            )
            .background(color = avatarColor.color, shape = CircleShape)
            .size(avatarSize)
    ) {
        BackgroundAwareText(
            text = firstName.firstOrNull()?.toString()?.uppercase() ?: "X",
            backgroundColor = avatarColor.color,
            fontSize = fontSize,
        )
    }
}

@Preview
@Composable
private fun AvatarPrev() {
    YTheme {
        Surface {
            Avatar(
                firstName = "TEST",
                avatarColor = ProfileColorEnum.DARK_BLUE,
                avatarSize = 100.dp,
                fontSize = 40.sp
            )
        }
    }
}