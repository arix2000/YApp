package com.y.app.features.post.data

import android.content.Context
import com.y.app.R
import com.y.app.features.home.data.models.Post
import com.y.app.features.login.data.models.User
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

data class Comment(
    val id: Int,
    val content: String,
    val author: User,
    val likesCount: Int,
    val isLikedByMe: Boolean,
    val date: String,
    val isNew: Boolean = false
) {
    val dateTime: LocalDateTime get() = LocalDateTime.parse(date)

    fun getDateTimeDisplayText(context: Context): String {
        val dateTime = LocalDateTime.parse(date)
        val currentDateTime = LocalDateTime.now()
        if (dateTime.toLocalDate().equals(currentDateTime.toLocalDate())) {
            val differenceInMinutes =
                Duration.between(currentDateTime, dateTime).toMinutes().absoluteValue
            return if (differenceInMinutes in (Post.MAX_MINUTES_TO_DISPLAY..Post.MAX_HOURS_IN_MINUTES_TO_DISPLAY)) context.getString(
                R.string.post_hours_ago_text, Duration.ofMinutes(differenceInMinutes).toHours()
            )
            else if (differenceInMinutes < Post.MAX_MINUTES_TO_DISPLAY) if (differenceInMinutes.minutes.toInt(
                    DurationUnit.MINUTES
                ) == 0
            ) context.getString(R.string.just_now)
            else context.getString(
                R.string.post_minutes_ago_text,
                differenceInMinutes.minutes.toInt(DurationUnit.MINUTES)
            )
            else dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }
}