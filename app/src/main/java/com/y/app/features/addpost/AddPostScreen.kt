package com.y.app.features.addpost

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.arix.pokedex.features.pokemon_details.presentation.ui.components.full_screen_image_dialog.FullScreenImageDialog
import com.y.app.R
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.addpost.ui.AddPostEvent
import com.y.app.features.addpost.ui.AddPostViewModel
import com.y.app.features.common.DefaultTopBar
import com.y.app.features.common.ErrorBanner
import com.y.app.features.common.NextButton
import com.y.app.features.common.YOutlinedTextField
import com.y.app.features.common.extensions.isUrl
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

private const val MAX_CHAR_COUNT = 500

@Composable
fun AddPostScreen(
    navigator: Navigator = koinInject(), viewModel: AddPostViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value
    if (state.postAdded != null) navigator.navigateToAndClearBackStack(
        Screen.HomeScreen, Screen.AddPostScreen
    )

    AddPostScreenContent(state.isLoading, navigator) { event -> viewModel.invokeEvent(event) }

    ErrorBanner(errorMessage = state.errorMessage)
}

@Composable
private fun AddPostScreenContent(
    isLoading: Boolean, navigator: Navigator, invokeEvent: (AddPostEvent) -> Unit
) {
    var contentText by remember { mutableStateOf("") }
    var contentTextCharCount by remember { mutableIntStateOf(0) }
    var isCharLimitExceeded by remember { mutableStateOf(false) }
    var contentNotEmptyText: String? by remember { mutableStateOf(null) }

    var imageUrl by remember { mutableStateOf("") }
    var isImageLoaded: Boolean by remember { mutableStateOf(false) }
    val isDialogShowed = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column {
        DefaultTopBar(title = "Add post", navigator = navigator)
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AddPostTextField(
                value = contentText,
                errorText = if (isCharLimitExceeded) stringResource(R.string.reached_char_limit) else contentNotEmptyText,
                textCount = contentTextCharCount,
                hint = stringResource(R.string.sup),
                modifier = Modifier.heightIn(min = 200.dp, max = 400.dp),
                onValueChanged = {
                    contentNotEmptyText =
                        if (it.isBlank()) context.getString(R.string.content_cannot_be_empty) else null
                    isCharLimitExceeded = it.length >= MAX_CHAR_COUNT
                    contentTextCharCount = it.length
                    if (!isCharLimitExceeded) contentText = it
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            AddPostTextField(value = imageUrl,
                errorText = if (imageUrl.isNotBlank() && !imageUrl.isUrl()) stringResource(R.string.invalid_url) else null,
                singleLine = true,
                hint = stringResource(R.string.image_url_hint),
                onValueChanged = {
                    imageUrl = it
                },
                trailing = {
                    IconButton(onClick = { imageUrl = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "delete url")
                    }
                })
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.onSecondary),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.preview_image),
                    modifier = Modifier
                        .heightIn(100.dp, 250.dp)
                        .fillMaxWidth()
                        .clickable(enabled = imageUrl.isUrl()) {
                            isDialogShowed.value = true
                        },
                    onSuccess = { isImageLoaded = true },
                    onError = { isImageLoaded = false },
                    onLoading = { isImageLoaded = false },
                    contentScale = ContentScale.FillWidth
                )
                if (!isImageLoaded) Text(text = stringResource(R.string.preview), fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            NextButton(
                text = "Add post", isLoading = isLoading,
                onClick = {
                    if (contentText.isBlank()) contentNotEmptyText =
                        context.getString(R.string.content_cannot_be_empty)
                    else invokeEvent(
                        AddPostEvent.AddPost(
                            contentText,
                            if (imageUrl.isBlank() || !imageUrl.isUrl()) null else imageUrl
                        )
                    )
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .width(144.dp),
            )
        }
    }
    FullScreenImageDialog(isDialogShowed = isDialogShowed, imageUrl = imageUrl)
}

@Composable
private fun ColumnScope.AddPostTextField(
    value: String,
    errorText: String?,
    hint: String,
    modifier: Modifier = Modifier,
    textCount: Int? = null,
    singleLine: Boolean = false,
    onValueChanged: (String) -> Unit,
    trailing: @Composable () -> Unit = {}
) {
    val isError = errorText != null
    YOutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        isError = isError,
        placeholder = { Text(text = hint) },
        singleLine = singleLine,
        trailingIcon = trailing
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.align(Alignment.End)
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .weight(1f, fill = isError)
        ) {
            if (isError) {
                Text(
                    text = errorText!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp
                )
            }
        }
        if (textCount != null) Text(
            text = "$textCount/$MAX_CHAR_COUNT", fontSize = 14.sp,
        )
    }
}

@Preview
@Composable
private fun AddPostScreenPreview() {
    YTheme {
        Surface {
            AddPostScreenContent(false, Navigator()) { }
        }
    }
}