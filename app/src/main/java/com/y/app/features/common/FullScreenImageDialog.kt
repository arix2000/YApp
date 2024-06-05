package com.arix.pokedex.features.pokemon_details.presentation.ui.components.full_screen_image_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.y.app.core.theme.YTheme
import com.y.app.features.common.FullScreenImageTopBar
import com.y.app.features.common.ZoomableBox

@Composable
fun FullScreenImageDialog(isDialogShowed: MutableState<Boolean>, imageUrl: String) {
    if (isDialogShowed.value)
        Dialog(
            onDismissRequest = { isDialogShowed.value = false },
            DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ZoomableBox(
                modifier = Modifier
                    .requiredWidth(LocalConfiguration.current.screenWidthDp.dp)
                    .fillMaxHeight()
                    .background(color = Color.Black.copy(alpha = 0.7f))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Pokemon Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .align(Alignment.Center)
                        .graphicsLayer(
                            scaleX = it.scale,
                            scaleY = it.scale,
                            translationX = it.offsetX,
                            translationY = it.offsetY
                        ),
                )
                FullScreenImageTopBar(title = "Image preview",
                    modifier = Modifier.align(Alignment.TopCenter),
                    onBackButtonClicked = { isDialogShowed.value = false })
            }
        }
}

@Preview
@Composable
private fun ImageFullScreenDialogPreview() {
    YTheme {
        Surface {
            FullScreenImageDialog(
                isDialogShowed = remember {
                    mutableStateOf(true)
                },
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/123.png"
            )
        }
    }
}