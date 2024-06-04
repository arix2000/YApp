package com.y.app.features.common.extensions

import androidx.compose.ui.graphics.Color
import com.y.app.features.registration.data.ProfileColorEnum

fun String.toProfileColor(): ProfileColorEnum {
    val color = Color(android.graphics.Color.parseColor(this))
    return ProfileColorEnum.entries.first { it.color == color }
}

fun String.withArgument(argumentName: String): String {
    return "$this/{$argumentName}"
}

fun String.putArgument(argumentName: String, argumentValue: String): String {
    return this.replace("{$argumentName}", argumentValue)
}