package com.y.app.features.common.extensions

import androidx.compose.ui.graphics.Color


fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}