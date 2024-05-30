package com.y.app.features.registration.data

import androidx.compose.ui.graphics.Color

enum class ProfileColorEnum(val color: Color) {
    DARK_BLUE(Color(0xFF0D47A1)),
    DARK_RED(Color(0xFFC62828)),
    DARK_GREEN(Color(0xFF2E7D32)),
    DARK_YELLOW(Color(0xFFF9A825)),
    DARK_ORANGE(Color(0xFFEF6C00)),
    DARK_PURPLE(Color(0xFF6A1B9A)),
    DARK_PINK(Color(0xFFAD1457)),
    DARK_CYAN(Color(0xFF00838F)),
    DARK_MAGENTA(Color(0xFF6A1B9A)),
    DARK_BROWN(Color(0xFF4E342E)),
    DARK_GRAY(Color(0xFF37474F)),
    DARK_TEAL(Color(0xFF00695C)),

    LIGHT_BLUE(Color(0xFF90CAF9)),
    LIGHT_RED(Color(0xFFEF9A9A)),
    LIGHT_GREEN(Color(0xFFA5D6A7)),
    LIGHT_YELLOW(Color(0xFFFFF59D)),
    LIGHT_ORANGE(Color(0xFFFFCC80)),
    LIGHT_PURPLE(Color(0xFFCE93D8)),
    LIGHT_PINK(Color(0xFFF48FB1)),
    LIGHT_CYAN(Color(0xFF80DEEA)),
    LIGHT_MAGENTA(Color(0xFFE1BEE7)),
    LIGHT_BROWN(Color(0xFFBCAAA4)),
    LIGHT_GRAY(Color(0xFFB0BEC5)),
    LIGHT_TEAL(Color(0xFF80CBC4));

    fun toHex(): String {
        with(color) {
            return String.format(
                "#%08X",
                (alpha * 255).toInt() shl 24 or
                        (red * 255).toInt() shl 16 or
                        (green * 255).toInt() shl 8 or
                        (blue * 255).toInt()
            )
        }
    }

    companion object {
        const val LIGHT_KEY = "LIGHT"
        const val DARK_KEY = "DARK"
    }
}
