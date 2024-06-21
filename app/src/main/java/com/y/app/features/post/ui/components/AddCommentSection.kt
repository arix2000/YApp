package com.y.app.features.post.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.author3
import com.y.app.core.theme.YTheme
import com.y.app.features.common.Avatar
import com.y.app.features.common.YOutlinedTextField
import com.y.app.features.common.extensions.toProfileColor
import com.y.app.features.login.data.models.User

@Composable
fun AddCommentSection(user: User, isAddLoading: Boolean, onAddClicked: (String) -> Unit) {
    var commentContent by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                firstName = user.name,
                avatarColor = user.avatarColor.toProfileColor(),
                avatarSize = 48.dp,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            YOutlinedTextField(value = commentContent,
                onValueChange = { commentContent = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                placeholder = { Text(text = stringResource(R.string.post_your_reply)) })
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier
                .clip(CircleShape)
                .clickable(enabled = commentContent.isNotBlank()) {
                    onAddClicked(commentContent)
                    commentContent = ""
                    focusManager.clearFocus()
                }
                .background(
                    if (commentContent.isNotBlank() || isAddLoading) MaterialTheme.colorScheme.primary else Color.DarkGray,
                    CircleShape
                )
                .padding(12.dp)
            ) {
                if (isAddLoading)
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                else
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = "Add comment",
                        tint = if (commentContent.isNotBlank()) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                    )
            }
        }
    }
}

@Preview
@Composable
private fun AddCommentSectionPreview() {
    YTheme {
        Surface {
            AddCommentSection(author3, true, { })
        }
    }
}