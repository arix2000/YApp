package com.y.app.features.common

import android.os.Build
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.y.app.core.theme.DarkColorScheme
import com.y.app.core.theme.LightColorScheme
import com.y.app.features.common.extensions.isDark

@Composable
fun BackgroundAwareText(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    style: TextStyle = LocalTextStyle.current
) {
    val context = LocalContext.current
    val textColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (backgroundColor.isDark()) dynamicDarkColorScheme(context).onBackground else dynamicLightColorScheme(
            context
        ).onBackground
    } else {
        if (backgroundColor.isDark()) DarkColorScheme.onBackground else LightColorScheme.onBackground
    }

    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        textAlign = textAlign,
        fontSize = fontSize,
        fontWeight = fontWeight,
        style = style
    )
}