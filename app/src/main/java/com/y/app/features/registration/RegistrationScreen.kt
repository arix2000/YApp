package com.y.app.features.registration

import android.widget.Toast
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.y.app.R
import com.y.app.core.navigation.Navigator
import com.y.app.core.navigation.Screen
import com.y.app.core.theme.YTheme
import com.y.app.features.common.BackButton
import com.y.app.features.common.ErrorBanner
import com.y.app.features.common.YOutlinedTextField
import com.y.app.features.login.data.models.RegistrationResult
import com.y.app.features.login.ui.NextButton
import com.y.app.features.registration.data.UserBodyUi
import com.y.app.features.registration.ui.RegistrationEvent
import com.y.app.features.registration.ui.RegistrationState
import com.y.app.features.registration.ui.RegistrationViewModel
import com.y.app.features.registration.ui.components.AvatarPersonalisationSection
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    navigator: Navigator = koinInject()
) {
    val state = viewModel.state.collectAsState().value
    val user = viewModel.userBodyUiState.collectAsState().value
    val context = LocalContext.current
    DisposableEffect(state.registrationResult) {
        if (state.registrationResult == RegistrationResult.OK) {
            Toast.makeText(
                context,
                context.getString(R.string.register_success_toast_message),
                Toast.LENGTH_LONG
            ).show()
            navigator.navigateToAndClearBackStack(Screen.LoginScreen)
        }
        onDispose { }
    }
    RegistrationScreenContent(state, user, { viewModel.invokeEvent(it) }, navigator)
}

@Composable
private fun RegistrationScreenContent(
    state: RegistrationState,
    user: UserBodyUi,
    invokeEvent: (RegistrationEvent) -> Unit,
    navigator: Navigator
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        BackButton(
            onClick = {
                navigator.popBackStack()
            }, modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(R.string.registration_title),
            fontSize = 30.sp,
            style = TextStyle(lineHeight = 35.sp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        RegisterTextField(
            value = user.firstName,
            label = stringResource(R.string.first_name_label),
            onValueChange = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(firstName = it)))
            },
            validationResult = user.validateNotEmpty(user.firstName)
        )
        RegisterTextField(
            value = user.lastName,
            label = stringResource(R.string.last_name_label),
            onValueChange = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(lastName = it)))
            },
            validationResult = user.validateNotEmpty(user.lastName)
        )

        RegisterTextField(
            value = user.email,
            label = stringResource(id = R.string.e_mail_label),
            onValueChange = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(email = it)))
            },
            validationResult = user.validateEmail()
        )
        RegisterTextField(
            value = user.password,
            label = stringResource(id = R.string.password_label),
            onValueChange = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(password = it)))
            },
            validationResult = user.validateNotEmpty(user.password),
            isPassword = true
        )
        RegisterTextField(
            value = user.repeatedPassword, label = "Verify password", onValueChange = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(repeatedPassword = it)))
            }, validationResult = user.validatePasswordRepeat(), isPassword = true, isLast = true
        )
        AvatarPersonalisationSection(
            user,
            invokeEvent,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        NextButton(
            text = stringResource(R.string.sign_up),
            isLoading = state.isLoading,
            onClick = {
                invokeEvent(RegistrationEvent.UpdateUser(user.copy(isValidating = true)))
                if (user.isAllValidated())
                    invokeEvent(RegistrationEvent.RegisterUser)
                else
                    coroutineScope.launch { scrollState.animateScrollTo(0, tween(500)) }
            },
            modifier = Modifier
                .align(Alignment.End)
                .width(126.dp),
        )
        Spacer(modifier = Modifier.height(100.dp))
    }
    ErrorBanner(state.errorMessage)
}

@Composable
private fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    validationResult: String?,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    isLast: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    return Column(modifier = modifier) {
        YOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            isError = validationResult != null,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPassword) PasswordVisualTransformation()
            else VisualTransformation.None,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = if (isLast) ImeAction.Default else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    focusManager.clearFocus()
                }
            ),
        )

        Box(modifier = Modifier.height(20.dp)) {
            if (validationResult != null) {
                Text(
                    text = validationResult,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }
        }

    }
}

@Preview
@Composable
private fun RegistrationScreenPreview() {
    YTheme {
        Surface {
            RegistrationScreenContent(
                RegistrationState(errorMessage = "Unexpected error"), UserBodyUi.EMPTY.copy(
                    isValidating = true,
                    email = "dsa@gmail",
                    password = "password0001",
                    repeatedPassword = "password0002"
                ), invokeEvent = {}, navigator = Navigator()
            )
        }
    }
}