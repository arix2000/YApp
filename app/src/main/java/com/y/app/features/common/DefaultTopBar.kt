package com.y.app.features.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.core.navigation.Navigator
import com.y.app.core.theme.YTheme

@Composable
fun DefaultTopBar(
    title: String,
    navigator: Navigator,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = { navigator.popBackStack() },
    trailingView: @Composable (() -> Unit)? = null
) {
    Row(
        modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onBackButtonClicked() }) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 22.sp)
        if (trailingView != null) {
            Spacer(modifier = Modifier.weight(1f))
            trailingView()
        }
    }
}

@Preview
@Composable
private fun DefaultTopBarPreview() {
    YTheme {
        Surface {
            DefaultTopBar(title = "Some title", Navigator(), trailingView = {
                Row {
                    Text(text = stringResource(R.string.log_out))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Logout,
                        contentDescription = "logout"
                    )
                }
            })
        }
    }
}