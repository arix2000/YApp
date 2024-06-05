package com.y.app.features.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.NextButton
import com.y.app.features.common.YOutlinedTextField
import com.y.app.features.login.ui.state.LoginEvent
import com.y.app.features.login.ui.state.LoginState
import com.y.app.features.login.ui.state.LoginViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun LoginScreen(viewModel: LoginViewModel = koinViewModel(), navigator: Navigator = koinInject()) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(true) {
        viewModel.invokeEvent(LoginEvent.LoginSavedUser)
    }
    LoginScreenContent(
        state = state,
        navigator = navigator,
        invokeEvent = { viewModel.invokeEvent(it) })
}

@Composable
private fun LoginScreenContent(
    state: LoginState, invokeEvent: (LoginEvent) -> Unit, navigator: Navigator
) {
    var emailText by remember { mutableStateOf("") }
    var passText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    DisposableEffect(state.user) {
        if (state.user != null)
            navigator.navigateToAndClearBackStack(Screen.HomeScreen, Screen.LoginScreen)
        onDispose { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .border(1.dp, MaterialTheme.colorScheme.onBackground, shape = CircleShape)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.logo_y_app),
            contentDescription = "Y logo"
        )
        Text(
            text = stringResource(R.string.login_screen_title),
            fontSize = 30.sp, style = TextStyle(lineHeight = 35.sp),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            YOutlinedTextField(
                value = emailText,
                onValueChange = {
                    emailText = it
                    invokeEvent(LoginEvent.ClearErrorMessage)
                },
                label = { Text(text = stringResource(R.string.e_mail_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
            )
            YOutlinedTextField(
                value = passText,
                onValueChange = {
                    passText = it
                    invokeEvent(LoginEvent.ClearErrorMessage)
                },
                label = { Text(text = stringResource(R.string.password_label)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                singleLine = true
            )
            SignUpButton(navigator)
            Spacer(modifier = Modifier.height(8.dp))
            NextButton(
                text = stringResource(R.string.sign_in_button_text),
                isLoading = state.isLoading,
                onClick = {
                    invokeEvent(LoginEvent.LoginUser(emailText, passText))
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .width(126.dp)
            )
        }
    }
    if (state.errorMessage != null)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 24.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = stringResource(R.string.login_screen_error_message),
                color = MaterialTheme.colorScheme.error
            )
        }
}

@Composable
fun SignUpButton(navigator: Navigator) {
    Box(
        modifier = Modifier
            .clickable { navigator.navigateTo(Screen.RegistrationScreen) }) {
        Text(
            text = stringResource(R.string.no_acount_button),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    YTheme {
        Surface {
            LoginScreenContent(LoginState(isLoading = false), { _ -> }, Navigator())
        }
    }
}