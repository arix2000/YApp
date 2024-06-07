package com.y.app.features.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.core.theme.LikeColor
import com.y.app.core.theme.YTheme

@Composable
fun LikeIndicator(
    isLiked: Boolean,
    onClicked: () -> Unit,
    likesCount: Int,
    modifier: Modifier = Modifier
) {
    val materialTheme = MaterialTheme.colorScheme
    var likeColor by remember {
        mutableStateOf(if (isLiked) LikeColor else materialTheme.onBackground)
    }
    val animatedLikeColor by animateColorAsState(
        targetValue = likeColor,
        label = "AnimatedLikeColor",
        animationSpec = tween(300, easing = LinearEasing)
    )
    DisposableEffect(key1 = isLiked) {
        likeColor = if (isLiked) LikeColor else materialTheme.onBackground
        onDispose { }
    }

    IndicatorRow(modifier = modifier, onClick = { onClicked() }) {
        AnimatedContent(
            targetState = isLiked,
            label = "SizeInSizeOutAnimFavourite",
            transitionSpec = {
                fadeIn(tween(500)) togetherWith fadeOut(tween(500))
            }) { isLikedByMe ->
            Icon(
                imageVector = if (isLikedByMe) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "likes",
                modifier = Modifier.size(17.dp),
                tint = animatedLikeColor
            )
        }
        Text(text = "$likesCount", fontSize = 14.sp, color = animatedLikeColor)
    }
}

@Preview
@Composable
private fun LikeIndicatorPreview() {
    YTheme {
        Surface {
            LikeIndicator(true, likesCount = 12, onClicked = {})
        }
    }
}