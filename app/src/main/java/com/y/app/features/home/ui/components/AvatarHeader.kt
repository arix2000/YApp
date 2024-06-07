package com.y.app.features.home.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.author1
import com.y.app.core.theme.YTheme
import com.y.app.features.common.Avatar
import com.y.app.features.common.extensions.toProfileColor
import com.y.app.features.login.data.models.User

@Composable
fun AvatarHeader(user: User, onClicked: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(
                CircleShape
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClicked()
            }) {
        Avatar(
            firstName = user.name,
            avatarColor = user.avatarColor.toProfileColor(),
            avatarSize = 28.dp,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = user.fullName,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun AvatarHeaderPreview() {
    YTheme {
        Surface {
            AvatarHeader(author1, {})
        }
    }
}