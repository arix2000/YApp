package com.y.app.features.home.data.models

import android.content.Context
import android.os.Parcelable
import com.y.app.R
import com.y.app.features.login.data.models.User
import kotlinx.parcelize.Parcelize
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

@Parcelize
data class Post(
    val id: Int,
    val author: User,
    val content: String,
    var likesCount: Int,
    val commentsCount: Int,
    var isLikedByMe: Boolean,
    val imageUrl: String?,
    private val date: String
) : Parcelable {

    val dateTime: LocalDateTime get() = LocalDateTime.parse(date)

    fun getDateTimeDisplayText(context: Context): String {
        val dateTime = LocalDateTime.parse(date)
        val currentDateTime = LocalDateTime.now()
        if (dateTime.toLocalDate().equals(currentDateTime.toLocalDate())) {
            val differenceInMinutes =
                Duration.between(currentDateTime, dateTime).toMinutes().absoluteValue
            return if (differenceInMinutes in (MAX_MINUTES_TO_DISPLAY..MAX_HOURS_IN_MINUTES_TO_DISPLAY))
                context.getString(
                    R.string.post_hours_ago_text,
                    Duration.ofMinutes(differenceInMinutes).toHours()
                )
            else if (differenceInMinutes < MAX_MINUTES_TO_DISPLAY)
                if (differenceInMinutes.minutes.toInt(DurationUnit.MINUTES) == 0)
                    context.getString(R.string.just_now)
                else
                    context.getString(
                        R.string.post_minutes_ago_text,
                        differenceInMinutes.minutes.toInt(DurationUnit.MINUTES)
                    )
            else dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }

    fun copyWithLikeChange(): Post {
        return copy(
            isLikedByMe = !isLikedByMe,
            likesCount = if (!isLikedByMe) likesCount.inc() else likesCount.dec()
        )
    }

    companion object {
        const val MAX_HOURS_IN_MINUTES_TO_DISPLAY = 480
        const val MAX_MINUTES_TO_DISPLAY = 60
    }
}