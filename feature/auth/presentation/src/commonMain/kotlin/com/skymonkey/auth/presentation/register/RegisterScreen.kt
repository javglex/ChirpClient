package com.skymonkey.auth.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skymonkey.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreenScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreenScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterScreenScreen(
            state = RegisterScreenState(),
            onAction = {}
        )
    }
}