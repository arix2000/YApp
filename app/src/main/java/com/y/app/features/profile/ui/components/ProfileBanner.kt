package com.y.app.features.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.author3
import com.y.app.core.theme.YTheme
import com.y.app.features.common.Avatar
import com.y.app.features.common.extensions.toProfileColor
import com.y.app.features.login.data.models.User

@Composable
fun ProfileBanner(user: User) {
    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.onSecondary, shape = RoundedCornerShape(
                    bottomEnd = 50.dp, bottomStart = 50.dp
                )
            )
            .height(150.dp)
            .fillMaxWidth()
            .padding(35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(
            firstName = user.name,
            avatarColor = user.avatarColor.toProfileColor(),
            avatarSize = 80.dp,
            fontSize = 40.sp
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = user.fullName, fontSize = 22.sp)
            Text(text = user.email)
        }
    }
}


@Preview
@Composable
private fun ProfileBannerPreview() {
    YTheme {
        Surface {
            ProfileBanner(author3)
        }
    }
}