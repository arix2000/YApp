package com.y.app.features.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.author1
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.extensions.toProfileColor
import com.y.app.features.login.data.models.User

@Composable
fun HomeTopBar(navigator: Navigator, user: User) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp)
    ) {
        Avatar(
            firstName = user.name,
            avatarColor = user.avatarColor.toProfileColor(),
            avatarSize = 40.dp,
            fontSize = 24.sp,
            modifier = Modifier.clickable {
                navigator.navigateTo(
                    Screen.ProfileScreen,
                    user.id.toString()
                )
            }
        )
        Image(
            painter = painterResource(id = R.drawable.logo_y_app),
            contentDescription = "",
            modifier = Modifier
                .height(44.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.onBackground, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Preview
@Composable
private fun AppTopBarPreview() {
    YTheme {
        Surface {
            HomeTopBar(Navigator(), user = author1)
        }
    }
}