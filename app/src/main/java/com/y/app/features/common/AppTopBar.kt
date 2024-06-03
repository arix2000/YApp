package com.y.app.features.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.y.app.R
import com.y.app.core.navigation.Navigator
import com.y.app.core.theme.YTheme

@Composable
fun AppTopBar(navigator: Navigator, isBackButton: Boolean = true) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        if (isBackButton)
            BackButton(onClick = { navigator.popBackStack() }, transparentBackground = true)
        else
            Spacer(modifier = Modifier.width(48.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_y_app),
            contentDescription = "",
            modifier = Modifier
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
            AppTopBar(Navigator(), isBackButton = true)
        }
    }
}