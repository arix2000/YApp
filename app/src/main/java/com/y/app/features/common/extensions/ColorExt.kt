package com.y.app.features.common.extensions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun Color.isDark(): Boolean {
    return this.luminance() < 0.4
}