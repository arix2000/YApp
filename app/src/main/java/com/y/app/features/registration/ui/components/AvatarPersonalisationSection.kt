package com.y.app.features.registration.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.core.theme.YTheme
import com.y.app.features.common.Avatar
import com.y.app.features.registration.data.ProfileColorEnum
import com.y.app.features.registration.data.UserBodyUi
import com.y.app.features.registration.ui.RegistrationEvent

@Composable
fun AvatarPersonalisationSection(
    user: UserBodyUi, invokeEvent: (RegistrationEvent) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.personalise_avatar_section_title),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Start)
        )
        Avatar(
            user.firstName,
            user.avatarColor,
            avatarSize = 100.dp,
            fontSize = 40.sp,
        )
        ColorsRotator(selectedColor = user.avatarColor,
            colors = ProfileColorEnum.entries.filter { it.name.contains(ProfileColorEnum.DARK_KEY) },
            onColorPicked = { color ->
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(avatarColor = color)))
            })
        ColorsRotator(selectedColor = user.avatarColor,
            colors = ProfileColorEnum.entries.filter { it.name.contains(ProfileColorEnum.LIGHT_KEY) },
            onColorPicked = { color ->
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(avatarColor = color)))
            })

    }
}

@Composable
fun ColorsRotator(
    selectedColor: ProfileColorEnum,
    colors: List<ProfileColorEnum>,
    onColorPicked: (ProfileColorEnum) -> Unit
) {
    LazyRow {
        items(colors) {
            Box(modifier = Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .background(it.color, shape = CircleShape)
                .border(
                    4.dp,
                    if (it.name == selectedColor.name)
                        MaterialTheme.colorScheme.onBackground
                    else Color.Transparent,
                    CircleShape
                )
                .clickable { onColorPicked(it) }
                .size(50.dp))
        }
    }
}

@Preview
@Composable
private fun AvatarPersonalisationSectionPrev() {
    YTheme {
        Surface {
            AvatarPersonalisationSection(user = UserBodyUi.EMPTY.copy(avatarColor = ProfileColorEnum.DARK_BLUE),
                invokeEvent = {})
        }
    }
}
