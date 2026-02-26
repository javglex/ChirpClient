package com.skymonkey.auth.presentation.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import chirpclient.feature.auth.presentation.generated.resources.Res
import chirpclient.feature.auth.presentation.generated.resources.email
import chirpclient.feature.auth.presentation.generated.resources.email_placeholder
import chirpclient.feature.auth.presentation.generated.resources.error_account_exists
import chirpclient.feature.auth.presentation.generated.resources.error_generic
import chirpclient.feature.auth.presentation.generated.resources.error_invalid_email
import chirpclient.feature.auth.presentation.generated.resources.error_invalid_password
import chirpclient.feature.auth.presentation.generated.resources.error_invalid_username
import chirpclient.feature.auth.presentation.generated.resources.login
import chirpclient.feature.auth.presentation.generated.resources.password
import chirpclient.feature.auth.presentation.generated.resources.password_hint
import chirpclient.feature.auth.presentation.generated.resources.register
import chirpclient.feature.auth.presentation.generated.resources.username
import chirpclient.feature.auth.presentation.generated.resources.username_hint
import chirpclient.feature.auth.presentation.generated.resources.username_placeholder
import chirpclient.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.skymonkey.core.designsystem.components.brand.ChirpBrandLogo
import com.skymonkey.core.designsystem.components.buttons.ChirpButton
import com.skymonkey.core.designsystem.components.buttons.ChirpButtonStyle
import com.skymonkey.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.skymonkey.core.designsystem.components.layouts.ChirpSnackbarScaffold
import com.skymonkey.core.designsystem.components.textfields.ChirpPasswordTextField
import com.skymonkey.core.designsystem.components.textfields.ChirpTextField
import com.skymonkey.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    RegisterScreenScreen(
        state = state,
        onAction = viewModel::onAction,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun RegisterScreenScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    ChirpSnackbarScaffold(
        snackBarHostState = snackbarHostState
    ) {
        ChirpAdaptiveFormLayout(
            headerText = stringResource(Res.string.welcome_to_chirp),
            errorText = null, // TODO error text
            logo = {
                ChirpBrandLogo()
            }
        ) {
            // username text field
            ChirpTextField(
                state = state.usernameTextState,
                placeholder = stringResource(Res.string.username_placeholder),
                title = stringResource(Res.string.username),
                supportingText = if(state.usernameError != null) {
                    state.usernameError.toResourceString()
                } else
                    stringResource(Res.string.username_hint),
                isError = state.usernameError != null,
                onFocusChanged = { isFocused ->
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // email text field
            ChirpTextField(
                state = state.emailTextState,
                placeholder = stringResource(Res.string.email_placeholder),
                title = stringResource(Res.string.email),
                supportingText = state.emailError?.toResourceString(),
                isError = state.emailError != null,
                onFocusChanged = { isFocused ->
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // password text field
            ChirpPasswordTextField(
                state = state.passwordTextState,
                placeholder = stringResource(Res.string.password),
                title = stringResource(Res.string.password),
                supportingText = if(state.passwordError != null) {
                    state.passwordError.toResourceString()
                } else
                    stringResource(Res.string.password_hint),
                isError = state.passwordError != null,
                isPasswordVisible = state.isPasswordVisible,
                onFocusChanged = { isFocused ->
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                },
                onToggleVisibility = {
                    onAction(RegisterScreenAction.OnTogglePasswordVisibilityClick)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            // register button
            ChirpButton(
                text = stringResource(Res.string.register),
                onClick = {
                    onAction(RegisterScreenAction.OnRegisterClick)
                },
                enabled = state.canRegister,
                isLoading = state.isRegistering,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(9.dp))
            // login button
            ChirpButton(
                text = stringResource(Res.string.login),
                onClick = {
                    onAction(RegisterScreenAction.OnLoginClick)
                },
                style = ChirpButtonStyle.SECONDARY,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun RegistrationErrorType.toResourceString(): String {
    val resource = when (this) {
        RegistrationErrorType.InvalidEmail -> Res.string.error_invalid_email
        RegistrationErrorType.UsernameExists -> Res.string.error_account_exists
        RegistrationErrorType.InvalidUsername -> Res.string.error_invalid_username
        RegistrationErrorType.InvalidPassword -> Res.string.error_invalid_password
        RegistrationErrorType.EmailExists -> Res.string.error_account_exists
    }
    return stringResource(resource)
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterScreenScreen(
            state = RegisterScreenState(),
            onAction = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}